package com.george.petclinicapplication.repositories;

import com.george.petclinicapplication.model.Specialty;
import org.springframework.data.repository.CrudRepository;

public interface SpecialtyRepository extends CrudRepository<Specialty, Long> {
}
