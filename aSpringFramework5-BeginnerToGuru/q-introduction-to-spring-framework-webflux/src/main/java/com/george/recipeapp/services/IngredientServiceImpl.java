package com.george.recipeapp.services;

import com.george.recipeapp.commands.IngredientCommand;
import com.george.recipeapp.converters.IngredientCommandToIngredient;
import com.george.recipeapp.converters.IngredientToIngredientCommand;
import com.george.recipeapp.domain.Ingredient;
import com.george.recipeapp.domain.Recipe;
import com.george.recipeapp.domain.UnitOfMeasure;
import com.george.recipeapp.repositories.reactive.RecipeReactiveRepository;
import com.george.recipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeReactiveRepository recipeReactiveRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecipeReactiveRepository recipeReactiveRepository, UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
    }

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        return recipeReactiveRepository.findById(recipeId)
                .flatMapIterable(Recipe::getIngredients)
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .single()
                .map(ingredient -> {
                    IngredientCommand command = ingredientToIngredientCommand.convert(ingredient);
                    command.setRecipeId(recipeId);
                    return command;
                });
    }

    @Override
    @Transactional
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) {
        Recipe recipe = recipeReactiveRepository.findById(command.getRecipeId())
                .switchIfEmpty(Mono.error(new RuntimeException("Recipe not found: " + command.getRecipeId())))
                .doOnError(thr -> log.error("error saving ingredient {}", command, thr))
                .toProcessor().block();

        unitOfMeasureReactiveRepository.findById(command.getUom().getId())
                .doOnNext(uom -> command.getUom().setDescription(uom.getDescription())).toProcessor().block();

        addOrUpdateIngredient(recipe, command);

        recipeReactiveRepository.save(recipe).toProcessor().block();

        Ingredient savedIngredient = findIngredient(recipe, command.getId())
                .orElseGet(() -> findIngredientByValue(recipe, command));
        IngredientCommand savedCommand = ingredientToIngredientCommand.convert(savedIngredient);
        savedCommand.setRecipeId(recipe.getId());
        return Mono.just(savedCommand);
    }

    private void addOrUpdateIngredient(Recipe recipe, IngredientCommand command) {
        Optional<Ingredient> ingredientOpt = findIngredient(recipe, command.getId());
        if (ingredientOpt.isPresent()) {
            updateIngredient(ingredientOpt.get(), command);
        } else {
            command.setId(UUID.randomUUID().toString());
            recipe.addIngredient(ingredientCommandToIngredient.convert(command));
        }
    }

    private void updateIngredient(Ingredient ingredient, IngredientCommand command) {
        ingredient.setDescription(command.getDescription());
        ingredient.setAmount(command.getAmount());
        UnitOfMeasure unitOfMeasure = getUnitOfMeasure(command).toProcessor().block();
        ingredient.setUom(unitOfMeasure);
    }

    private Mono<UnitOfMeasure> getUnitOfMeasure(IngredientCommand command) {
        String uomId = command.getUom().getId();
        return unitOfMeasureReactiveRepository.findById(uomId);
    }

    @Override
    public Mono<Void> deleteById(String recipeId, String id) {
        log.info("deleting ingredient {} from recipe {}", id, recipeId);
        return recipeReactiveRepository.findById(recipeId)
                .map(recipe -> {
                    recipe.getIngredients().removeIf(ingredient -> ingredient.getId().equals(id));
                    return recipe;
                })
                .flatMap(recipeReactiveRepository::save)
                .doOnError(error -> log.error("error deleting ingredient {} from recipe {}", id, recipeId, error))
                .then();
    }

    private Optional<Ingredient> findIngredient(Recipe recipe, String id) {
        return recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .findAny();
    }

//    private Mono<Ingredient> findIngredient(Recipe recipe, String id) {
//        return Flux.fromIterable(recipe.getIngredients())
//                .filter(ingredient -> ingredient.getId().equals(id))
//                .next();
//    }

    private Ingredient findIngredientByValue(Recipe recipe, IngredientCommand command) {
        return recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getDescription().equals(command.getDescription()))
                .filter(ingredient -> ingredient.getAmount().equals(command.getAmount()))
                .filter(ingredient -> ingredient.getUom().getId().equals(command.getUom().getId()))
                .findFirst()
                .orElse(null);
    }
}
