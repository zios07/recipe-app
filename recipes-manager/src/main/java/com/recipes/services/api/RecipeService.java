package com.recipes.services.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.recipes.domain.Recipe;
import com.recipes.services.exception.RecipeNotFoundException;

public interface RecipeService {

    Page<Recipe> getAllRecipes(Pageable pageable);

    Recipe findRecipeById(int id) throws RecipeNotFoundException;

    Recipe createRecipe(Recipe recipe);

    Recipe updateRecipe(Recipe recipe) throws RecipeNotFoundException;

    void deleteRecipe(int id) throws RecipeNotFoundException;

}
