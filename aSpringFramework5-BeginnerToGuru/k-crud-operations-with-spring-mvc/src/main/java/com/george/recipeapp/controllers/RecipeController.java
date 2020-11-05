package com.george.recipeapp.controllers;

import com.george.recipeapp.commands.RecipeCommand;
import com.george.recipeapp.services.CategoryService;
import com.george.recipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {

    private final RecipeService recipeService;
    private final CategoryService categoryService;


    private static final String RECIPE_RECIPE_FORM_URL = "recipe/recipeform";
    private static final String RECIPE_ATTRIBUTE = "recipe";

    public RecipeController(RecipeService recipeService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute(RECIPE_ATTRIBUTE, recipeService.findById(Long.parseLong(id)));

        return "recipe/show";
    }

    @RequestMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute(RECIPE_ATTRIBUTE, new RecipeCommand());

        return RECIPE_RECIPE_FORM_URL;
    }

    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute(RECIPE_ATTRIBUTE, recipeService.findCommandById(Long.valueOf(id)));
        model.addAttribute("categoriesList", categoryService.listAllCategories());

        return  RECIPE_RECIPE_FORM_URL;
    }

    //@RequestMapping(name = "recipe", method = RequestMethod.POST) -OLD Spring 4 Way
    @PostMapping(RECIPE_ATTRIBUTE)
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }
}
