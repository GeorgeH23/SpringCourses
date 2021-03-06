package com.george.recipeapp.converters;

import com.george.recipeapp.commands.RecipeCommand;
import com.george.recipeapp.domain.Category;
import com.george.recipeapp.domain.Ingredient;
import com.george.recipeapp.domain.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryConverter;
    private final IngredientCommandToIngredient ingredientConverter;
    private final NotesCommandToNotes notesConverter;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConveter, IngredientCommandToIngredient ingredientConverter,
                                 NotesCommandToNotes notesConverter) {
        this.categoryConverter = categoryConveter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null) {
            return null;
        }
        Recipe recipe = new Recipe();
        if (!StringUtils.isEmpty(source.getId())) {
            recipe.setId(source.getId());
        }
        recipe.setDescription(source.getDescription());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setCookTime(source.getCookTime());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setDirections(source.getDirections());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setNotes(notesConverter.convert(source.getNotes()));
        recipe.setImage(source.getImage());

        Set<Ingredient> ingredients = source.getIngredients().stream()
                .map(ingredientConverter::convert)
                .collect(Collectors.toSet());
        recipe.setIngredients(ingredients);

        Set<Category> categories = source.getCategories().stream()
                .map(categoryConverter::convert)
                .collect(Collectors.toSet());
        recipe.setCategories(categories);

        return recipe;
    }
}
