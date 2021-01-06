package com.george.petclinicapplication.services.map;

import com.george.petclinicapplication.model.Specialty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SpecialtyMapServiceTest {

    SpecialtyMapService specialtyMapService;
    final Long radiologySpecialtyId = 1L;
    final Long surgerySpecialtyId = 2L;

    @BeforeEach
    void setUp() {
        specialtyMapService = new SpecialtyMapService();
        Specialty radiology = Specialty.builder().id(radiologySpecialtyId).description("Radiology").build();
        Specialty surgery = Specialty.builder().id(surgerySpecialtyId).description("Surgery").build();
        specialtyMapService.save(radiology);
        specialtyMapService.save(surgery);
    }

    @Test
    void findAll() {
        Set<Specialty> specialties = specialtyMapService.findAll();

        assertEquals(2, specialties.size());
    }

    @Test
    void findById() {
        Specialty spec1 = specialtyMapService.findById(radiologySpecialtyId);
        Specialty spec2 = specialtyMapService.findById(surgerySpecialtyId);

        assertEquals(1L, spec1.getId());
        assertEquals(2L, spec2.getId());
    }

    @Test
    void saveExistingId() {
        Long id = 3L;

        Specialty spec3 = Specialty.builder().id(id).build();
        Specialty savedSpecialty = specialtyMapService.save(spec3);

        assertEquals(id, savedSpecialty.getId());
    }

    @Test
    void saveNoId() {
        Specialty savedSpecialty = specialtyMapService.save(Specialty.builder().build());

        assertNotNull(savedSpecialty);
        assertNotNull(savedSpecialty.getId());
    }

    @Test
    void delete() {
        specialtyMapService.delete(specialtyMapService.findById(radiologySpecialtyId));

        assertEquals(1, specialtyMapService.findAll().size());
    }

    @Test
    void deleteByID() {
        specialtyMapService.deleteByID(surgerySpecialtyId);

        assertEquals(1, specialtyMapService.findAll().size());
    }
}