package com.george.petclinicapplication.repositories;

import com.george.petclinicapplication.model.Vet;
import org.springframework.data.repository.CrudRepository;

public interface VetRepository extends CrudRepository<Vet, Long> {
}
