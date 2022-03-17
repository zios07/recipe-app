package com.recipes.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recipes.domain.Recipe;
import com.recipes.services.api.RecipeService;
import com.recipes.services.exception.RecipeNotFoundException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    public Page<Recipe> getRecipes(@RequestParam(defaultValue = "0") final int page,
                    @RequestParam(defaultValue = "20") final int size) {
        return recipeService.getAllRecipes(PageRequest.of(page, size));
    }

    @GetMapping(value = "{id}")
    public Recipe getRecipeById(@PathVariable final int id) throws RecipeNotFoundException {
        return recipeService.findRecipeById(id);
    }

    @PostMapping
    public Recipe createRecipe(@RequestBody final Recipe recipe) {
        return recipeService.createRecipe(recipe);
    }

    @PutMapping
    public Recipe updateRecipe(@RequestBody final Recipe recipe) throws RecipeNotFoundException {
        return recipeService.updateRecipe(recipe);
    }

    @DeleteMapping(value = "{id}")
    public void deleteRecipe(@PathVariable final int id) throws RecipeNotFoundException {
        recipeService.deleteRecipe(id);
    }

}
