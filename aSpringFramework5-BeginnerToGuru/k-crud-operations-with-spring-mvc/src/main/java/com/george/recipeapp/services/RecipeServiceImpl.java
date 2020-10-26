package com.george.recipeapp.services;

import com.george.recipeapp.domain.Recipe;
import com.george.recipeapp.exceptions.RecipeNotFoundException;
import com.george.recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm in the RecipeService.");

        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long id) {
        Optional<Recipe> recipeOptional =  recipeRepository.findById(id);

        if (recipeOptional.isEmpty()) {
            throw new RecipeNotFoundException("Recipe Not Found");
        }

        return recipeOptional.get();
    }
}
