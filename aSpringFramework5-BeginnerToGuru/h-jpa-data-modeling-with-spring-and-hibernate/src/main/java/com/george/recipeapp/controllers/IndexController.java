package com.george.recipeapp.controllers;

import com.george.recipeapp.domain.Category;
import com.george.recipeapp.domain.UnitOfMeasure;
import com.george.recipeapp.repositories.CategoryRepository;
import com.george.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage() {

        Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        /*System.out.println("Category ID is: " + categoryOptional.get().getId());
        System.out.println("UOM ID is: " + unitOfMeasureOptional.get().getId());*/

        categoryOptional.ifPresent(category -> System.out.println("Category ID is: " + category.getId()));
        unitOfMeasureOptional.ifPresent(unitOfMeasure -> System.out.println("UOM ID is: " + unitOfMeasure.getId()));

        return "index";
    }
}
