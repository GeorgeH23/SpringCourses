package com.george.petclinicapplication.repositories;

import com.george.petclinicapplication.model.Visit;
import org.springframework.data.repository.CrudRepository;

public interface VisitRepository extends CrudRepository<Visit, Long> {
}
