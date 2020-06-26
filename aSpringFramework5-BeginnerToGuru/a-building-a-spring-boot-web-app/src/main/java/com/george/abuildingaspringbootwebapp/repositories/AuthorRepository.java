package com.george.abuildingaspringbootwebapp.repositories;

import com.george.abuildingaspringbootwebapp.domain.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {

}
