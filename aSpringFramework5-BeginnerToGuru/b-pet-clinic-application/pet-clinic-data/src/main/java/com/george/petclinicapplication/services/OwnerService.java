package com.george.petclinicapplication.services;

import com.george.petclinicapplication.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {

    Owner findByLastName(String lastName);
}
