package com.recipes.services.impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.recipes.domain.Recipe;
import com.recipes.repositories.RecipeRepository;
import com.recipes.services.api.RecipeService;
import com.recipes.services.exception.RecipeNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Override public Page<Recipe> getAllRecipes(final Pageable pageable) {
        log.info("Retrieving all recipes");
        return recipeRepository.findAll(pageable);
    }

    @Override public Recipe findRecipeById(final int id) throws RecipeNotFoundException {
        log.info("Retrieving recipe with id : {}", id);
        return recipeRepository.findById(id).orElseThrow(() -> new RecipeNotFoundException("Requested recipe could "
                        + "not be found"));
    }

    @Override public Recipe createRecipe(final Recipe recipe) {
        log.info("Creating new recipe");
        recipe.setCreatedAt(LocalDateTime.now());
        return recipeRepository.save(recipe);
    }

    @Override public Recipe updateRecipe(final Recipe recipe) throws RecipeNotFoundException {
        if (!recipeRepository.existsById(recipe.getId())) {
            throw new RecipeNotFoundException("Trying to update a recipe that does not exist");
        }
        log.info("Updating recipe with id : {}", recipe.getId());
        return recipeRepository.save(recipe);
    }

    @Override public void deleteRecipe(final int id) throws RecipeNotFoundException {
        if (!recipeRepository.existsById(id)) {
            throw new RecipeNotFoundException("Trying to delete a recipe that does not exist");
        }
        log.info("Deleting recipe with id : {}", id);
        recipeRepository.deleteById(id);
    }
}
