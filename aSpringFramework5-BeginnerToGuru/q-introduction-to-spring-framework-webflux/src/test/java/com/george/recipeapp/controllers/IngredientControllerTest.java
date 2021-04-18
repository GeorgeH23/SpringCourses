package com.george.recipeapp.controllers;

import com.george.recipeapp.commands.IngredientCommand;
import com.george.recipeapp.commands.RecipeCommand;
import com.george.recipeapp.commands.UnitOfMeasureCommand;
import com.george.recipeapp.services.IngredientService;
import com.george.recipeapp.services.RecipeService;
import com.george.recipeapp.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@WebFluxTest(controllers = IngredientController.class)
@Import(ThymeleafAutoConfiguration.class)
class IngredientControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private RecipeService recipeService;

    @MockBean
    private IngredientService ingredientService;

    @MockBean
    private UnitOfMeasureService unitOfMeasureService;

    IngredientController controller;
    UnitOfMeasureCommand ounceUnit;
    IngredientCommand ingredient1;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);

        ounceUnit = new UnitOfMeasureCommand();
        ounceUnit.setDescription("Ounce");
        ounceUnit.setId(UUID.randomUUID().toString());

        ingredient1 = new IngredientCommand();
        ingredient1.setId("1358136a8f4dbd51e");
        ingredient1.setDescription("Salt");
        ingredient1.setAmount(BigDecimal.valueOf(3));
        ingredient1.setUom(ounceUnit);
    }

    @Test
    void testListIngredients() {
        // given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setDescription("Recipe description");
        recipeCommand.setIngredients(Collections.singletonList(ingredient1));
        when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(recipeCommand));

        // when
        webTestClient.get().uri("/recipe/1/ingredients")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(body -> {
                    assertThat(body, containsString("Salt"));
                    assertThat(body, containsString("3 Ounce"));
                });

        // then
        verify(recipeService, times(1)).findCommandById("1");
    }

    @Test
    void testShowIngredient() {
        // given
        when(ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString())).thenReturn(Mono.just(ingredient1));

        // when
        webTestClient.get()
                .uri("/recipe/1/ingredient/2/show")
                .exchange()
                .expectBody(String.class)
                .value(body -> {
                    assertThat(body, containsString("Salt"));
                    assertThat(body, containsString("3 Ounce"));
                });

        // then
        verify(ingredientService, times(1)).findByRecipeIdAndIngredientId("1", "2");
    }

    @Test
    void testNewIngredientForm() {
        // given
        String recipeId = "1234321";
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipeId);
        when(recipeService.findCommandById(recipeId)).thenReturn(Mono.just(recipeCommand));
        when(unitOfMeasureService.listAllUoms()).thenReturn(Flux.empty());

        // when + then
        webTestClient.get()
                .uri("/recipe/1234321/ingredient/new")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(body -> {
                    assertThat(body, containsString("/recipe/" + recipeId));
                });

        verify(recipeService).findCommandById(recipeId);
    }

    @Test
    void testUpdateIngredientForm() {
        // given
        String recipeId = "a1f3d2";
        ingredient1.setRecipeId(recipeId);
        when(ingredientService.findByRecipeIdAndIngredientId("1", "2")).thenReturn(Mono.just(ingredient1));
        when(unitOfMeasureService.listAllUoms()).thenReturn(Flux.just(ounceUnit));

        // when
        webTestClient.get()
                .uri("/recipe/1/ingredient/2/update")
                .exchange()
                .expectBody(String.class)
                .value(body -> {
                    assertThat(body, containsString("/recipe/" + recipeId));
                    assertThat(body, containsString(ingredient1.getId()));
                    assertThat(body, containsString(ounceUnit.getDescription()));
                    assertThat(body, containsString(ounceUnit.getId()));
                });
    }

    @Test
    void testSaveOrUpdate() {
        // given
        String ingredientId = "5123123123";
        String description = "Sugar";
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setDescription(description);
        ingredientCommand.setId(ingredientId);
        ingredientCommand.setRecipeId("12");
        when(ingredientService.saveIngredientCommand(any(IngredientCommand.class))).thenReturn(Mono.just(ingredientCommand));

        // when
        webTestClient.post()
                .uri("/recipe/12/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("id", ingredientId)
                        .with("description", description)
                        .with("amount", "2")
                        .with("uom.id", "5fa2eb39e80c2c5def8da99f"))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .valueEquals("Location", "/recipe/12/ingredient/5123123123/show");

        ArgumentCaptor<IngredientCommand> captor = ArgumentCaptor.forClass(IngredientCommand.class);
        verify(ingredientService).saveIngredientCommand(captor.capture());
        IngredientCommand savedIngredient = captor.getValue();
        assertThat(savedIngredient.getId(), is(ingredientCommand.getId()));
        assertThat(savedIngredient.getDescription(), is(ingredientCommand.getDescription()));
        assertThat(savedIngredient.getRecipeId(), is(ingredientCommand.getRecipeId()));
    }

    @Test
    void testDeleteIngredient() {
        when(ingredientService.deleteById(any(), any())).thenReturn(Mono.empty());

        // when + then
        webTestClient.get()
                .uri("/recipe/7/ingredient/37/delete")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .valueEquals("Location", "/recipe/7/ingredients");

        // then
        verify(ingredientService).deleteById("7", "37");
    }

}





































