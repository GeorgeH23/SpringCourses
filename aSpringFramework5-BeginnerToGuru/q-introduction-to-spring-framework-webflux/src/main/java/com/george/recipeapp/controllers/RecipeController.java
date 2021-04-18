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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.thymeleaf.exceptions.TemplateInputException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Controller
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;
    private final CategoryService categoryService;

    private static final String RECIPE_RECIPE_FORM_URL = "recipe/recipeform";
    private static final String RECIPE_ATTRIBUTE = "recipe";

    public RecipeController(RecipeService recipeService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @GetMapping("/recipe/{id}/show")
    public Mono<String> showById(@PathVariable String id, Model model) {
        return recipeService.findById(id)
                .doOnNext(recipe -> model.addAttribute(RECIPE_ATTRIBUTE, recipe))
                .map(rec -> "recipe/show")
                .switchIfEmpty(Mono.error(new RecipeNotFoundException("recipe not found: " + id)));
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
    public Mono<String> saveOrUpdate(@Valid @ModelAttribute(RECIPE_ATTRIBUTE) Mono<RecipeCommand> command) {

        return command
                .flatMap(recipeService::saveRecipeCommand)
                .map(recipe -> "redirect:/recipe/" + recipe.getId() + "/show")
                .doOnError(thr -> log.error("Error saving recipe"))
                .onErrorResume(WebExchangeBindException.class, thr -> Mono.just(RECIPE_RECIPE_FORM_URL));
    }

    @GetMapping(value = "recipe/{id}/delete")
    public Mono<String> deleteById(@PathVariable String id) {
        log.debug("Deleting recipe {}", id);
        return recipeService.deleteById(id)
                .thenReturn("redirect:/");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({RecipeNotFoundException.class, TemplateInputException.class})
    public String handleNotFound(Exception exception, Model model) {
        log.error("Handling not found exception {}", exception.getMessage());

        model.addAttribute("exception", exception);

        return "404Error";
    }

    @ModelAttribute("categoriesList")
    public Flux<CategoryCommand> populateCategoryList() {
        return categoryService.listAllCategories();
    }
}
