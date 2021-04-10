package com.george.recipeapp.services;

import com.george.recipeapp.domain.Recipe;
import com.george.recipeapp.repositories.reactive.RecipeReactiveRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ImageServiceImplTest {

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    ImageService imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        imageService = new ImageServiceImpl(recipeReactiveRepository);
    }

    @Test
    void saveImageFile() throws IOException {
        //given
        String id = "1";
        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeReactiveRepository.findById(id)).thenReturn(Mono.just(recipe));
        when(recipeReactiveRepository.save(recipe)).thenReturn(Mono.empty());

        byte[] bytes = "Some test bytes".getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = new DefaultDataBufferFactory().allocateBuffer();
        buffer.write(bytes);
        // when
        imageService.saveImageFile(id, Flux.just(buffer)).block();

        // then
        ArgumentCaptor<Recipe> recipeCaptor = ArgumentCaptor.forClass(Recipe.class);
        verify(recipeReactiveRepository).save(recipeCaptor.capture());
        Recipe savedRecipe = recipeCaptor.getValue();
        Assert.assertEquals(bytes.length, savedRecipe.getImage().length);
    }
}