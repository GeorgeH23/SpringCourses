package com.george.petclinicapplication.repositories;

import com.george.petclinicapplication.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

    Owner findByLastName(String lastName);

    List<Owner> findByLastNameLikeIgnoreCase(String lastName);
}
