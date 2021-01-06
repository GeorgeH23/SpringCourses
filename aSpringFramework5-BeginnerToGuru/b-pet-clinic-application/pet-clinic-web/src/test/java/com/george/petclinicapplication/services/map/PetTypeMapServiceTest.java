package com.george.petclinicapplication.services.map;

import com.george.petclinicapplication.model.PetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PetTypeMapServiceTest {

    PetTypeMapService petTypeMapService;
    final Long petType1Id = 1L;
    final Long petType2Id = 2L;

    @BeforeEach
    void setUp() {
        petTypeMapService = new PetTypeMapService();
        PetType dog = PetType.builder().id(petType1Id).name("Dog").build();
        PetType cat = PetType.builder().id(petType2Id).name("Cat").build();
        petTypeMapService.save(dog);
        petTypeMapService.save(cat);
    }

    @Test
    void findAll() {
        Set<PetType> petTypes = petTypeMapService.findAll();

        assertEquals(2, petTypes.size());
    }

    @Test
    void findById() {
        PetType petType1 = petTypeMapService.findById(petType1Id);
        PetType petType2 = petTypeMapService.findById(petType2Id);

        assertEquals(1L, petType1.getId());
        assertEquals(2L, petType2.getId());
    }

    @Test
    void saveExistingId() {
        Long id = 3L;

        PetType petType3 = PetType.builder().id(id).build();
        PetType savedPetType = petTypeMapService.save(petType3);

        assertEquals(id, savedPetType.getId());
    }

    @Test
    void saveNoId() {
        PetType savedPetType = petTypeMapService.save(PetType.builder().build());

        assertNotNull(savedPetType);
        assertNotNull(savedPetType.getId());
    }

    @Test
    void delete() {
        petTypeMapService.delete(petTypeMapService.findById(petType1Id));

        assertEquals(1, petTypeMapService.findAll().size());
    }

    @Test
    void deleteByID() {
        petTypeMapService.deleteByID(petType2Id);

        assertEquals(1, petTypeMapService.findAll().size());
    }
}