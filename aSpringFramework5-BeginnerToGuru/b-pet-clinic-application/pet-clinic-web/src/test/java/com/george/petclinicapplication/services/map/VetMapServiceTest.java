package com.george.petclinicapplication.services.map;

import com.george.petclinicapplication.model.Vet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class VetMapServiceTest {

    VetMapService vetMapService;
    final Long vet1Id = 1L;
    final Long vet2Id = 2L;

    @BeforeEach
    void setUp() {
        vetMapService = new VetMapService(new SpecialtyMapService());

        vetMapService.save(Vet.builder().id(vet1Id).build());
        vetMapService.save(Vet.builder().id(vet2Id).build());
    }

    @Test
    void findAll() {
        Set<Vet> vetsSet = vetMapService.findAll();
        assertEquals(2, vetsSet.size());
    }

    @Test
    void findById() {
        Vet vet = vetMapService.findById(vet1Id);

        assertEquals(vet1Id, vet.getId());
    }

    @Test
    void saveExistingId() {
        Long id = 3L;
        Vet vet3 = Vet.builder().id(id).build();

        Vet savedVet = vetMapService.save(vet3);

        assertEquals(id, savedVet.getId());
    }

    @Test
    void saveNoId() {
        Vet savedVet = vetMapService.save(Vet.builder().build());

        assertNotNull(savedVet);
        assertNotNull(savedVet.getId());
    }

    @Test
    void delete() {
        vetMapService.delete(vetMapService.findById(vet1Id));

        assertEquals(1, vetMapService.findAll().size());
    }

    @Test
    void deleteByID() {
        vetMapService.deleteByID(vet2Id);

        assertEquals(1, vetMapService.findAll().size());
    }
}