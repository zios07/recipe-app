package com.recipes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipes.domain.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
}
