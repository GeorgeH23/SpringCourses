package com.george.petclinicapplication.repositories;

import com.george.petclinicapplication.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
