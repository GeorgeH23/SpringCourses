package com.george.abuildingaspringbootwebapp.repositories;


import com.george.abuildingaspringbootwebapp.domain.Publisher;
import org.springframework.data.repository.CrudRepository;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
}
