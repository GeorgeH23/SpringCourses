package com.george.petclinicapplication.services;

import com.george.petclinicapplication.model.Owner;

import java.util.Set;

public interface OwnerService extends CrudService<Owner, Long> {

    Owner findByLastName(String lastName);
}
