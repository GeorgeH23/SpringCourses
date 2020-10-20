package com.george.petclinicapplication.services.map;

import com.george.petclinicapplication.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    OwnerMapService ownerMapService;
    final Long owner1Id = 1L;
    final Long owner2Id = 2L;
    final String lastName = "Smith";

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());

        ownerMapService.save(Owner.builder().id(owner1Id).build());
        ownerMapService.save(Owner.builder().id(owner2Id).lastName(lastName).build());
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = ownerMapService.findAll();
        assertEquals(2, ownerSet.size());
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(owner1Id);

        assertEquals(owner1Id, owner.getId());
    }

    @Test
    void saveExistingId() {
        Long id = 3L;
        Owner owner3 = Owner.builder().id(id).build();

        Owner savedOwner = ownerMapService.save(owner3);

        assertEquals(id, savedOwner.getId());
    }

    @Test
    void saveNoId() {
        Owner savedOwner = ownerMapService.save(Owner.builder().build());

        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void delete() {
        ownerMapService.delete(ownerMapService.findById(owner1Id));

        assertEquals(1, ownerMapService.findAll().size());
    }

    @Test
    void deleteByID() {
        ownerMapService.deleteByID(owner1Id);

        assertEquals(1, ownerMapService.findAll().size());
    }

    @Test
    void findByLastName() {
        Owner smith = ownerMapService.findByLastName(lastName);

        assertNotNull(smith);
        assertEquals(owner2Id, smith.getId());
    }

    @Test
    void findByLastNameNotFound() {
        String lastName = "foo";
        Owner owner = ownerMapService.findByLastName(lastName);

        assertNull(owner);
    }
}