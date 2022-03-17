package com.recipes.controllers;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipes.AbstractContainerBaseTest;
import com.recipes.domain.Recipe;
import com.recipes.repositories.RecipeRepository;
import com.recipes.services.api.RecipeService;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RecipeControllerTest extends AbstractContainerBaseTest {

    private MockMvc mvc;

    @Autowired private ObjectMapper mapper;

    @Autowired private RecipeService recipeService;

    @Autowired private RecipeRepository recipeRepository;

    @Autowired private WebApplicationContext context;

    @BeforeEach public void setup() {

        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

        recipeRepository.deleteAll();
        recipeService.createRecipe(Recipe.builder().name("Recipe 1").createdAt(LocalDateTime.now())
                        .ingredients(Arrays.asList("Water", "Sugar", "Farine")).build());

    }

    @Test @WithMockUser public void shouldGetRecipes() throws Exception {
        mvc.perform(get("/recipes").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.content", hasSize(1)));

    }

    @Test @WithMockUser public void shouldNotBeAbleToCreateNewRecipeAsUser() throws Exception {

        final Recipe recipe = Recipe.builder().name("Recipe 2").createdAt(LocalDateTime.now())
                        .ingredients(Arrays.asList("Ingredient 1", "Ingredient 2", "Ingredient 3")).build();

        mvc.perform(post("/recipes").content(mapper.writeValueAsString(recipe)).contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isForbidden());

    }

    @Test @WithMockUser(roles = "ADMIN") public void shouldBeAbleToCreateNewRecipeAsAdmin() throws Exception {

        final Recipe recipe = Recipe.builder().name("Recipe 2").createdAt(LocalDateTime.now())
                        .ingredients(Arrays.asList("Ingredient 1", "Ingredient 2", "Ingredient 3")).build();

        mvc.perform(post("/recipes").content(mapper.writeValueAsString(recipe)).contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.name", is(recipe.getName())));

        assertEquals(2, recipeRepository.count());

    }

    @Test @WithMockUser(roles = "ADMIN") public void shouldBeAbleToUpdateExistingRecipeAsAdmin() throws Exception {

        final Recipe recipe = recipeService.getAllRecipes(PageRequest.of(0, 10)).getContent().get(0);
        recipe.setName(recipe.getName().concat(" EDITED"));

        mvc.perform(put("/recipes").content(mapper.writeValueAsString(recipe)).contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.name", is(recipe.getName())));

        assertEquals(1, recipeRepository.count());

    }

    @Test @WithMockUser(roles = "ADMIN") public void shouldBeAbleToDeleteARecipeAsAdmin() throws Exception {

        final Recipe recipe = recipeService.getAllRecipes(PageRequest.of(0, 10)).getContent().get(0);

        mvc.perform(delete("/recipes/" + recipe.getId())).andExpect(status().isOk());

        assertEquals(0, recipeRepository.count());

    }

}
