package com.george.recipeapp.controllers;

import com.george.recipeapp.commands.RecipeCommand;
import com.george.recipeapp.domain.Notes;
import com.george.recipeapp.domain.Recipe;
import com.george.recipeapp.services.CategoryService;
import com.george.recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebFluxTest(controllers = RecipeController.class)
@Import(ThymeleafAutoConfiguration.class)
class RecipeControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    RecipeService recipeService;

    @MockBean
    CategoryService categoryService;

    RecipeController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new RecipeController(recipeService, categoryService);
    }

    @Test
    void testGetRecipe() {
        String recipeNotes = "These are the recipe notes";
        Recipe recipe = new Recipe();
        recipe.setDescription("Lasagne");
        recipe.setId("1");
        Notes notes = new Notes();
        notes.setRecipeNotes(recipeNotes);
        recipe.setNotes(notes);

        when(recipeService.findById(anyString())).thenReturn(Mono.just(recipe));

        webTestClient.get().uri("/recipe/1/show")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(body -> {
                    assertThat(body, containsString("Lasagne"));
                    assertThat(body, containsString(recipeNotes));
                });
    }

    @Test
    void testGetRecipeNotFound() {
        Recipe recipe = new Recipe();
        recipe.setId("1");

        when(recipeService.findById(anyString())).thenReturn(Mono.empty());

//        webTestClient.get().uri("/recipe/1/show")
//                .exchange()
//                .expectStatus().isNotFound();
    }

    @Test
    void testGetNewRecipeForm() {
        RecipeCommand recipe = new RecipeCommand();
        recipe.setId("1");

//        mockMvc.perform(get("/recipe/new"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("recipe/recipeform"))
//                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testPostNewRecipe() {
        RecipeCommand command = new RecipeCommand();
        command.setId("5");

        when(recipeService.saveRecipeCommand(any())).thenReturn(Mono.just(command));

//        mockMvc.perform(post("/recipe")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param("id", "")
//                .param("description", "a description")
//                .param("directions", "some direction")
//        )
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/recipe/5/show"));
    }

    @Test
    void testPostNewRecipeFormValidationFail() {
        RecipeCommand command = new RecipeCommand();
        command.setId("5");

        when(recipeService.saveRecipeCommand(any())).thenReturn(Mono.just(command));

//        mockMvc.perform(post("/recipe")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param("id", "")
//        )
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("recipe"))
//                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    void testGetUpdateView() {
        RecipeCommand recipe = new RecipeCommand();
        recipe.setId("7");

        when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(recipe));

//        mockMvc.perform(get("/recipe/1/update"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("recipe/recipeform"))
//                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testGetDeleteAction() {
        when(recipeService.deleteById(anyString())).thenReturn(Mono.empty());

        webTestClient.get().uri("/recipe/1/delete")
                .exchange()
                .expectStatus().is3xxRedirection();

        verify(recipeService, times(1)).deleteById("1");
    }
}