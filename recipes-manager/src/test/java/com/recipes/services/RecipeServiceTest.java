package com.recipes.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.recipes.AbstractContainerBaseTest;
import com.recipes.domain.Recipe;
import com.recipes.repositories.RecipeRepository;
import com.recipes.services.api.RecipeService;
import com.recipes.services.exception.RecipeNotFoundException;
import com.recipes.services.impl.RecipeServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class RecipeServiceTest extends AbstractContainerBaseTest {

    @Mock private RecipeRepository recipeRepository;

    private RecipeService recipeService;

    private static List<Recipe> recipes = new ArrayList<>();

    @BeforeEach public void setup() {

        recipeService = new RecipeServiceImpl(recipeRepository);

        // Mock recipes
        recipes = Arrays.asList(Recipe.builder().id(1).name("Recipe 1").createdAt(LocalDateTime.now())
                                        .ingredients(Arrays.asList("Ingredient 1", "Ingredient 2")).build(),

                        Recipe.builder().id(2).name("Recipe 2").createdAt(LocalDateTime.now())
                                        .ingredients(Arrays.asList("Ingredient 3", "Ingredient 4")).build());

    }

    // Get all recipes

    @Test public void getAllRecipesTest() {
        when(recipeRepository.findAll(isA(Pageable.class))).thenReturn(new PageImpl<>(recipes));
        final Page<Recipe> recipesPage = recipeService.getAllRecipes(PageRequest.of(0, 10));
        Assert.assertEquals(recipesPage.getContent().size(), recipes.size());
    }

    // Get recipe by id

    @Test public void getSingleRecipeSuccessTest() throws RecipeNotFoundException {

        // Getting a recipe with id that does exist

        when(recipeRepository.findById(recipes.get(1).getId())).then((invocationOnMock) -> Optional.of(recipes.get(1)));

        final Recipe recipe = recipeService.findRecipeById(recipes.get(1).getId());
        Assert.assertEquals(recipes.get(1), recipe);
    }

    @Test public void getSingleRecipeNotFoundTest() {

        // Getting a recipe with id that does not exist

        when(recipeRepository.findById(recipes.get(0).getId())).then((invocationOnMock) -> Optional.empty());

        assertThrows(RecipeNotFoundException.class, () -> recipeService.findRecipeById(recipes.get(0).getId()),
                        "Expected findRecipeById() to throw RecipeNotFoundException, but it didn't");
    }

    // Create recipe

    @Test public void createRecipeTest() {
        given(recipeRepository.save(recipes.get(0))).willReturn(recipes.get(0));

        final Recipe recipe = recipeService.createRecipe(recipes.get(0));
        assertEquals(recipes.get(0), recipe);
    }

    // Update recipe

    @Test public void updateRecipeSuccessTest() throws RecipeNotFoundException {

        // Updating a recipe that exists in the db

        when(recipeRepository.existsById(recipes.get(0).getId())).then((invocationOnMock) -> true);
        when(recipeRepository.save(recipes.get(0))).then((invocationOnMock) -> recipes.get(0));

        final Recipe recipe = recipeService.updateRecipe(recipes.get(0));
        assertEquals(recipes.get(0), recipe);
    }

    @Test public void updateRecipeNotFoundTest() {

        // Updating a recipe that does not exist in the db

        when(recipeRepository.existsById(recipes.get(0).getId())).then((invocationOnMock) -> false);

        assertThrows(RecipeNotFoundException.class, () -> recipeService.updateRecipe(recipes.get(0)),
                        "Expected updateRecipe() to throw RecipeNotFoundException, but it didn't");
    }

    // Deleting recipe

    @Test public void deleteRecipeSuccessTest() throws RecipeNotFoundException {

        // Deleting a recipe that exists in the db

        when(recipeRepository.existsById(recipes.get(0).getId())).then((invocationOnMock) -> true);

        recipeService.deleteRecipe(recipes.get(0).getId());

        verify(recipeRepository).deleteById(any());
    }

    @Test public void deleteRecipeNotFoundTest() {

        // Deleting a recipe that does not exist in the db

        when(recipeRepository.existsById(recipes.get(0).getId())).then((invocationOnMock) -> false);

        assertThrows(RecipeNotFoundException.class, () -> recipeService.deleteRecipe(recipes.get(0).getId()),
                        "Expected deleteRecipe() to throw RecipeNotFoundException, but it didn't");
    }

}
