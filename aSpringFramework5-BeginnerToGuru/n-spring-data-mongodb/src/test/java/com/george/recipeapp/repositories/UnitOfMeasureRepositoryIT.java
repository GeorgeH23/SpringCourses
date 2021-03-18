package com.george.recipeapp.repositories;

import com.george.recipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UnitOfMeasureRepositoryIT {
/*
    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void findByDescription() {

        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        uomOptional.ifPresent(unitOfMeasure -> assertEquals("Teaspoon", unitOfMeasure.getDescription()));
    }


    @Test
    //@DirtiesContext
    void findByDescriptionCup() {

        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Cup");

        uomOptional.ifPresent(unitOfMeasure -> assertEquals("Cup", unitOfMeasure.getDescription()));
    }*/
}