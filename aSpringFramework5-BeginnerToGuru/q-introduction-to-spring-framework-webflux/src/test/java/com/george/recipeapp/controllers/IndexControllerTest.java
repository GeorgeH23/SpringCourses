package com.george.recipeapp.controllers;

import com.george.recipeapp.domain.Recipe;
import com.george.recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = IndexController.class)
@Import(ThymeleafAutoConfiguration.class)
class IndexControllerTest {

    IndexController controller;

    @MockBean
    private RecipeService recipeService;

    @Autowired
    private WebTestClient webTestClient;

    @Mock
    Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        controller = new IndexController(recipeService);
    }

    @Test
    void testIndex() {
        // given
        Recipe expectedRecipe1 = new Recipe();
        expectedRecipe1.setDescription("Fajita");

        Recipe expectedRecipe2 = new Recipe();
        expectedRecipe2.setDescription("Taco");

        when(recipeService.getRecipes()).thenReturn(Flux.just(expectedRecipe1, expectedRecipe2));

        // when

        // then
        webTestClient.get().uri("/index")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> {
                    System.out.println(response.getResponseBody());
                    String responseBody = Objects.requireNonNull(response.getResponseBody());
                    assertTrue(responseBody.contains("Fajita"));
                    assertTrue(responseBody.contains("Taco"));
                });

        verify(recipeService, times(1)).getRecipes();
    }

    @Test
    void getIndexPage() {
        // given
        Recipe recipe1 = new Recipe();
        recipe1.setId("1");
        Recipe recipe2 = new Recipe();
        recipe2.setId("2");

        when(recipeService.getRecipes()).thenReturn(Flux.just(recipe1, recipe2));

        ArgumentCaptor<Flux<Recipe>> argCaptor = ArgumentCaptor.forClass(Flux.class);

        // when
        String templateName = controller.getIndexPage(model);

        // then
        assertEquals("index", templateName);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argCaptor.capture());
        List<Recipe> modelRecipes = argCaptor.getValue().collectList().block();
        assertEquals(2, modelRecipes.size());
    }
}