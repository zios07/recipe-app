package com.recipes.services.exception;

public class RecipeNotFoundException extends Exception {
    public RecipeNotFoundException(final String message) {
        super(message);
    }
}
