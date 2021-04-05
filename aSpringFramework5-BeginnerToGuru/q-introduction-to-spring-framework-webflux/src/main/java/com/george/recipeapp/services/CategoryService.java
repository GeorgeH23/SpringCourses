package com.george.recipeapp.services;

import com.george.recipeapp.commands.CategoryCommand;

import java.util.Set;

public interface CategoryService {

    Set<CategoryCommand> listAllCategories();
}
