package com.george.recipeapp.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        Long id = 4L;

        category.setId(id);

        assertEquals(id, category.getId());
    }

    @Test
    void getDescription() {
        category.setDescription("new recipe");

        assertEquals("new recipe", category.getDescription());
    }

    @Test
    void getRecipes() {

        Set<Recipe> recipes = new HashSet<>();
        category.setRecipes(recipes);

        assertNotNull(category.getRecipes());
    }
}