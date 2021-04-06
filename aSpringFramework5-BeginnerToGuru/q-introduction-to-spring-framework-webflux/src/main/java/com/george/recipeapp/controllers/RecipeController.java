package com.george.recipeapp.controllers;

import com.george.recipeapp.commands.RecipeCommand;
import com.george.recipeapp.services.CategoryService;
import com.george.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        model.addAttribute("categoriesList", categoryService.listAllCategories());

        return RECIPE_RECIPE_FORM_URL;
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute(RECIPE_ATTRIBUTE, recipeService.findCommandById(id));
        model.addAttribute("categoriesList", categoryService.listAllCategories());

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

    /*@ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RecipeNotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        log.error("Handling not found exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }*/
}
