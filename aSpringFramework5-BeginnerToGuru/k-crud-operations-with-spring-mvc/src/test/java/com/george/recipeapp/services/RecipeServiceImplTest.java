package com.george.recipeapp.services;

import com.george.recipeapp.domain.Recipe;
import com.george.recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    void getRecipeById() throws RuntimeException {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe returnedRecipe = recipeService.findById(1L);

        assertNotNull(returnedRecipe, "Null recipe returned");
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    void getRecipes() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);

        // when findAll() method is called return to the test the recipesData set
        when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1);
        // verifies that findAll() method was called only once
        verify(recipeRepository, times(1)).findAll();
    }
}