package com.recipes;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.recipes.domain.Recipe;
import com.recipes.repositories.RecipeRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class RecipesManagerApplication implements CommandLineRunner {

    private final RecipeRepository recipeRepository;

    public static void main(final String[] args) {
        SpringApplication.run(RecipesManagerApplication.class, args);
    }

    @Override public void run(final String... args) throws Exception {
        if (recipeRepository.count() == 0) {
            final Recipe recipe1 = Recipe.builder().id(1).name("Recipe 1").createdAt(LocalDateTime.now())
                            .ingredients(Arrays.asList("Ingredient 1", "Ingredient 2")).build();

            final Recipe recipe2 = Recipe.builder().id(2).name("Recipe 2").createdAt(LocalDateTime.now())
                            .ingredients(Arrays.asList("Ingredient 3", "Ingredient 4")).build();

            recipeRepository.saveAll(Arrays.asList(recipe1, recipe2));
        }
    }
}
