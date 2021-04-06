package com.george.recipeapp.services;

import com.george.recipeapp.commands.CategoryCommand;
import reactor.core.publisher.Flux;

public interface CategoryService {

    Flux<CategoryCommand> listAllCategories();
}
