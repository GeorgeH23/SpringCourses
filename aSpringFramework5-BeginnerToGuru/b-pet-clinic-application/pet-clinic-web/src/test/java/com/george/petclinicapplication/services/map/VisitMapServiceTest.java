package com.george.petclinicapplication.services.map;

import com.george.petclinicapplication.model.Owner;
import com.george.petclinicapplication.model.Pet;
import com.george.petclinicapplication.model.Visit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class VisitMapServiceTest {

    VisitMapService visitMapService;
    final Long visit1Id = 1L;
    final Long visit2Id = 2L;

    @BeforeEach
    void setUp() {
        visitMapService = new VisitMapService();

        visitMapService.save(Visit.builder().id(visit1Id).pet(Pet.builder().id(1L).owner(Owner.builder().id(1L).build()).build()).build());
        visitMapService.save(Visit.builder().id(visit2Id).pet(Pet.builder().id(2L).owner(Owner.builder().id(2L).build()).build()).build());
    }

    @Test
    void findAll() {
        Set<Visit> visits = visitMapService.findAll();

        assertEquals(2, visits.size());
    }

    @Test
    void findById() {
        Visit visit = visitMapService.findById(visit1Id);

        assertEquals(visit1Id, visit.getId());
    }

    @Test
    void saveExistingId() {
        Long id = 3L;
        Visit visit3 = Visit.builder().id(id).pet(Pet.builder().id(3L).owner(Owner.builder().id(3L).build()).build()).build();

        Visit savedVisit = visitMapService.save(visit3);

        assertEquals(id, savedVisit.getId());
    }

    @Test
    void saveNoId() {
        Visit savedVisit = visitMapService.save(Visit.builder().pet(Pet.builder().id(3L).owner(Owner.builder().id(3L).build()).build()).build());

        assertNotNull(savedVisit);
        assertNotNull(savedVisit.getId());
    }

    @Test
    void delete() {
        visitMapService.delete(visitMapService.findById(visit1Id));

        assertEquals(1, visitMapService.findAll().size());
    }

    @Test
    void deleteByID() {
        visitMapService.deleteByID(visit2Id);

        assertEquals(1, visitMapService.findAll().size());
    }
}