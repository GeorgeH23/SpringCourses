package com.george.recipeapp.services;

import com.george.recipeapp.commands.IngredientCommand;
import com.george.recipeapp.commands.UnitOfMeasureCommand;
import com.george.recipeapp.converters.IngredientCommandToIngredient;
import com.george.recipeapp.converters.IngredientToIngredientCommand;
import com.george.recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.george.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.george.recipeapp.domain.Ingredient;
import com.george.recipeapp.domain.Recipe;
import com.george.recipeapp.domain.UnitOfMeasure;
import com.george.recipeapp.repositories.reactive.RecipeReactiveRepository;
import com.george.recipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    IngredientService ingredientService;

    //init converters
    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient,
                recipeReactiveRepository, unitOfMeasureReactiveRepository);
    }

    @Test
    void findByRecipeIdAndRecipeIdHappyPath() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId("1");

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId("1");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId("2");

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId("3");

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("1", "3").block();

        //when
        assertEquals("3", ingredientCommand.getId());
        verify(recipeReactiveRepository, times(1)).findById(anyString());
    }

    @Test
    void testSaveRecipeCommand() {
        // given
        String recipeId = "2";
        String ingredientId = "3";

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId("135421");
        uom.setDescription("Gallon");
        when(unitOfMeasureReactiveRepository.findById(uom.getId())).thenReturn(Mono.just(uom));

        IngredientCommand command = new IngredientCommand();
        command.setId(ingredientId);
        command.setRecipeId(recipeId);
        UnitOfMeasureCommand uomc = new UnitOfMeasureCommand();
        uomc.setId(uom.getId());
        command.setUom(uomc);

        Recipe savedRecipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        savedRecipe.addIngredient(ingredient);

        when(recipeReactiveRepository.findById(recipeId)).thenReturn(Mono.just(savedRecipe));
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(savedRecipe));
        // when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();

        // then
        assertEquals(ingredientId, savedCommand.getId());
        verify(recipeReactiveRepository, times(1)).findById(recipeId);
        verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));

    }

    @Test
    void testUpdateIngredient() {
        // given
        String recipeId = "2";
        String ingredientId = "3";

        UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
        uomCommand.setId("0");
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId("0");
        when(unitOfMeasureReactiveRepository.findById("0")).thenReturn(Mono.just(uom));

        IngredientCommand command = new IngredientCommand();
        command.setId(ingredientId);
        command.setRecipeId(recipeId);
        command.setUom(uomCommand);

        Recipe savedRecipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        ingredient.setUom(new UnitOfMeasure());
        savedRecipe.addIngredient(ingredient);
        when(recipeReactiveRepository.findById(recipeId)).thenReturn(Mono.just(savedRecipe));
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(savedRecipe));

        // when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();

        // then
        Assert.assertEquals(ingredientId, savedCommand.getId());
        verify(recipeReactiveRepository).findById(recipeId);
        verify(recipeReactiveRepository).save(any(Recipe.class));
    }

    @Test
    void testDeleteById() {
        //given
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId("3");
        recipe.addIngredient(ingredient);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(recipe));

        //when
        ingredientService.deleteById("1", "3").block();

        //then
        verify(recipeReactiveRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));
    }
}