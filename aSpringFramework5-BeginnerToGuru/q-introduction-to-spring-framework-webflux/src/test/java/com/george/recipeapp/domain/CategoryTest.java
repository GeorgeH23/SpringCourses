package com.george.recipeapp.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {

    Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        String id = "4";

        category.setId(id);

        assertEquals(id, category.getId());
    }

    @Test
    void getDescription() {
        category.setDescription("new recipe");

        assertEquals("new recipe", category.getDescription());
    }
}