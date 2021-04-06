package com.george.recipeapp.services;

import com.george.recipeapp.commands.CategoryCommand;
import com.george.recipeapp.converters.CategoryToCategoryCommand;
import com.george.recipeapp.repositories.CategoryRepository;
import com.george.recipeapp.repositories.reactive.CategoryReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryReactiveRepository categoryReactiveRepository;
    private final CategoryToCategoryCommand categoryToCategoryCommand;

    public CategoryServiceImpl(CategoryReactiveRepository categoryReactiveRepository, CategoryToCategoryCommand categoryToCategoryCommand) {
        this.categoryReactiveRepository = categoryReactiveRepository;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Override
    public Flux<CategoryCommand> listAllCategories() {
        return categoryReactiveRepository.findAll()
                .map(categoryToCategoryCommand::convert);
    }
}
