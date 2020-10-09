package com.george.recipeapp.controllers;

import com.george.recipeapp.domain.Category;
import com.george.recipeapp.domain.UnitOfMeasure;
import com.george.recipeapp.repositories.CategoryRepository;
import com.george.recipeapp.repositories.UnitOfMeasureRepository;
import com.george.recipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {

        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}
