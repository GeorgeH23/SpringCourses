package com.george.petclinicapplication.services;

import com.george.petclinicapplication.model.Pet;

import java.util.Set;

public interface PetService {

    Pet findById(Long id);

    Pet save(Pet pet);

    Set<Pet> findAll();
}
