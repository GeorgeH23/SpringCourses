package com.george.recipeapp.exceptions;

public class RecipeNotFoundException extends RuntimeException {

    public RecipeNotFoundException(String message, Exception exception) {
        super(message, exception);
    }

    public RecipeNotFoundException (String message) {
        super(message);
    }
}
