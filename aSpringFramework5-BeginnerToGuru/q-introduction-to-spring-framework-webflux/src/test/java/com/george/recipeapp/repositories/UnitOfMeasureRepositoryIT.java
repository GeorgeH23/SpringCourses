package com.george.recipeapp.repositories;

import com.george.recipeapp.bootstrap.RecipeBootstrap;
import com.george.recipeapp.repositories.reactive.CategoryReactiveRepository;
import com.george.recipeapp.repositories.reactive.RecipeReactiveRepository;
import com.george.recipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.test.StepVerifier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataMongoTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;

    @BeforeEach
    void setUp() {
        recipeReactiveRepository.deleteAll();
        unitOfMeasureReactiveRepository.deleteAll();
        categoryReactiveRepository.deleteAll();

        RecipeBootstrap recipeBootstrap = new RecipeBootstrap(recipeReactiveRepository, unitOfMeasureReactiveRepository,
                categoryReactiveRepository);

        recipeBootstrap.onApplicationEvent(null);
    }

    @Test
    void findByDescription() {
        StepVerifier.create(unitOfMeasureReactiveRepository.findByDescription("Teaspoon"))
                .assertNext(uom -> assertThat(uom.getDescription(), is("Teaspoon")));
    }


    @Test
    void findByDescriptionCup() {
        StepVerifier.create(unitOfMeasureReactiveRepository.findByDescription("Cup"))
                .assertNext(uom -> assertThat(uom.getDescription(), is("Cup")));
    }
}