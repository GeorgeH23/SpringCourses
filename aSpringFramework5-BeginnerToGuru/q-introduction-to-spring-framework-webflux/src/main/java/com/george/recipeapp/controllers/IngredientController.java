package com.george.recipeapp.controllers;

import com.george.recipeapp.commands.IngredientCommand;
import com.george.recipeapp.commands.UnitOfMeasureCommand;
import com.george.recipeapp.services.IngredientService;
import com.george.recipeapp.services.RecipeService;
import com.george.recipeapp.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    private static final String INGREDIENT_FORM = "recipe/ingredient/ingredientform";

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model) {
        log.debug("Getting ingredient list for recipe {}", recipeId);
        model.addAttribute("recipe", recipeService.findCommandById(recipeId));

        return "recipe/ingredient/list";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
        log.debug("Getting ingredient list for recipe {}", recipeId);
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id));

        return "recipe/ingredient/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model) {
        Mono<IngredientCommand> ingredient = recipeService.findCommandById(recipeId)
                .map(recipeCommand -> {
                    IngredientCommand ingredientCommand = new IngredientCommand();
                    ingredientCommand.setRecipeId(recipeCommand.getId());
                    ingredientCommand.setUom(new UnitOfMeasureCommand());
                    return ingredientCommand;
                });
        model.addAttribute("ingredient", ingredient);

        return INGREDIENT_FORM;
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id));

        return INGREDIENT_FORM;
    }

    @PostMapping(value = "/recipe/{recipeId}/ingredient")
    public Mono<String> saveOrUpdate(@Valid @ModelAttribute("ingredient") Mono<IngredientCommand> ingredient,
                                     @PathVariable String recipeId, Model model) {
        return ingredient.doOnNext(cmd -> cmd.setRecipeId(recipeId))
                .flatMap(ingredientService::saveIngredientCommand)
                .doOnNext(sc -> log.debug("saved recipe id:{} and ingredient id:{}", sc.getRecipeId(),
                        sc.getId()))
                .map(sc -> "redirect:/recipe/" + sc.getRecipeId() + "/ingredient/" + sc.getId() + "/show")
                .onErrorResume(WebExchangeBindException.class, thr -> {
                    ((IngredientCommand)model.getAttribute("ingredient")).setRecipeId(recipeId);
                    return Mono.just(INGREDIENT_FORM);
                })
                .doOnError(thr -> log.error("Error saving ingredient for recipe {}", recipeId));
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
    public Mono<String> deleteIngredient(@PathVariable String recipeId, @PathVariable String id) {
        log.debug("removing ingredient {} from recipe id:{}", id, recipeId);
        return ingredientService.deleteById(recipeId, id).toProcessor()
                .thenReturn("redirect:/recipe/" + recipeId + "/ingredients");
    }

    @ModelAttribute("uomList")
    public Flux<UnitOfMeasureCommand> populateUomList() {
        return unitOfMeasureService.listAllUoms();
    }

}











































