package com.george.recipeapp.exceptions;

public class UnitOfMeasureException extends RuntimeException {

    public UnitOfMeasureException(String message, Exception exception) {
        super(message, exception);
    }

    public UnitOfMeasureException (String message) {
        super(message);
    }
}
