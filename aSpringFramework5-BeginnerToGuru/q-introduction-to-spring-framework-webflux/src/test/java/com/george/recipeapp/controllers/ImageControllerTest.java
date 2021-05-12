package com.george.recipeapp.controllers;

import com.george.recipeapp.commands.RecipeCommand;
import com.george.recipeapp.services.ImageService;
import com.george.recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@WebFluxTest(controllers = ImageController.class)
@Import(ThymeleafAutoConfiguration.class)
class ImageControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private ImageService imageService;

    @MockBean
    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        ImageController controller = new ImageController(imageService, recipeService);
    }

    @Test
    void testImageForm() {
        // given
        String recipeId = "42";
        RecipeCommand recipe = new RecipeCommand();
        recipe.setId(recipeId);
        when(recipeService.findCommandById(recipeId)).thenReturn(Mono.just(recipe));

        // when
        webTestClient.get()
                .uri("/recipe/42/image")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(body -> {
                    assertThat(body.toLowerCase(), containsString("upload"));
                    assertThat(body, containsString("/recipe/42/image"));
                });
    }

    @Test
    void handleImagePost() {
        // given
        when(imageService.saveImageFile(eq("1"), any())).thenReturn(Mono.empty());

        byte[] fakeImage = "Spring Framework Guru".getBytes();
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("imagefile", fakeImage, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "form-data; name=imagefile; filename=image.jpg");

        webTestClient.post()
                .uri("/recipe/1/image")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/recipe/1/show");
        verify(imageService).saveImageFile(eq("1"), any());
    }

    @Test
    void readImageFromRepository() {

        // given
        String recipeId = "6";
        byte[] content = "some content".getBytes();
        RecipeCommand command = new RecipeCommand();
        command.setId(recipeId);
        command.setImage(content);

        when(recipeService.findCommandById(recipeId)).thenReturn(Mono.just(command));

        // when
        webTestClient.get()
                .uri("/recipe/" + recipeId + "/recipeimage")
                .exchange()
                .expectStatus().isOk()
                .expectBody(byte[].class)
                .value(body -> {
                    assertThat(body.length, is(content.length));
                });
    }

}




































