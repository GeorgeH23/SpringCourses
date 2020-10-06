package com.george.petclinicapplication.repositories;

import com.george.petclinicapplication.model.PetType;
import org.springframework.data.repository.CrudRepository;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {
}
