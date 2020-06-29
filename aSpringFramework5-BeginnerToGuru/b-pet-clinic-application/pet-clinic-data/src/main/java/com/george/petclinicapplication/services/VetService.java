package com.george.petclinicapplication.services;

import com.george.petclinicapplication.model.Vet;

import java.util.Set;

public interface VetService {

    Vet findById(Long id);

    Vet save(Vet vet);

    Set<Vet> findAll();
}
