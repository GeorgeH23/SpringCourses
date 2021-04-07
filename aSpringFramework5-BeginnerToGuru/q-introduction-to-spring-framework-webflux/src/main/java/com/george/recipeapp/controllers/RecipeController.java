package com.george.recipeapp.controllers;

import com.george.recipeapp.commands.CategoryCommand;
import com.george.recipeapp.commands.RecipeCommand;
import com.george.recipeapp.exceptions.RecipeNotFoundException;
import com.george.recipeapp.services.CategoryService;
import com.george.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateInputException;
import reactor.core.publisher.Flux;

@Controller
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;
    private final CategoryService categoryService;

    private WebDataBinder webDataBinder;

    private static final String RECIPE_RECIPE_FORM_URL = "recipe/recipeform";
    private static final String RECIPE_ATTRIBUTE = "recipe";

    public RecipeController(RecipeService recipeService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        this.webDataBinder = webDataBinder;
    }

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute(RECIPE_ATTRIBUTE, recipeService.findById(id).toProcessor());

        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute(RECIPE_ATTRIBUTE, new RecipeCommand());

        return RECIPE_RECIPE_FORM_URL;
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute(RECIPE_ATTRIBUTE, recipeService.findCommandById(id));

        return  RECIPE_RECIPE_FORM_URL;
    }

    @PostMapping(RECIPE_ATTRIBUTE)
    public String saveOrUpdate(@ModelAttribute(RECIPE_ATTRIBUTE) RecipeCommand command) {

        webDataBinder.validate();
        BindingResult bindingResult = webDataBinder.getBindingResult();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return RECIPE_RECIPE_FORM_URL;
        }

        RecipeCommand recipeCommand = recipeService.saveRecipeCommand(command).toProcessor().block();

        return "redirect:/recipe/" + recipeCommand.getId() + "/show";
    }

    @GetMapping(value = "recipe/{id}/delete")
    public String deleteById(@PathVariable String id) {
        log.debug("Deleting id: " + id);

        recipeService.deleteById(id);
        return "redirect:/index";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({RecipeNotFoundException.class, TemplateInputException.class})
    public String handleNotFound(Exception exception, Model model) {

        log.error("Handling not found exception");
        log.error(exception.getMessage());

        model.addAttribute("exception", exception);

        return "404Error";
    }

    @ModelAttribute("categoriesList")
    public Flux<CategoryCommand> populateCategoryList() {
        return categoryService.listAllCategories();
    }
}
