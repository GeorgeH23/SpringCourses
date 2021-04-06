package com.george.recipeapp.services;

import com.george.recipeapp.commands.RecipeCommand;
import com.george.recipeapp.converters.RecipeCommandToRecipe;
import com.george.recipeapp.converters.RecipeToRecipeCommand;
import com.george.recipeapp.domain.Recipe;
import com.george.recipeapp.repositories.reactive.RecipeReactiveRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

@SpringBootTest
class RecipeServiceIT {

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;


    @Test
    void testSaveOfDescription() {
        // given
        Recipe testRecipe = recipeReactiveRepository.findAll().blockFirst();
        RecipeCommand testCommand = recipeToRecipeCommand.convert(Objects.requireNonNull(testRecipe));

        // when
        String testDescription = "This is the test description";
        Objects.requireNonNull(testCommand).setDescription(testDescription);
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(testCommand).block();

        // then
        Assert.assertEquals(testDescription, Objects.requireNonNull(savedCommand).getDescription());
        Assert.assertEquals(testCommand.getCategories().size(), savedCommand.getCategories().size());
        Assert.assertEquals(testCommand.getIngredients().size(), savedCommand.getIngredients().size());
    }
}
