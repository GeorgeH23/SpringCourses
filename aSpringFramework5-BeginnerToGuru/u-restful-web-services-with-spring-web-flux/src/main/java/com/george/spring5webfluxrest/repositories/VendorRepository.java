package com.george.spring5webfluxrest.repositories;

import com.george.spring5webfluxrest.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {

    Mono<Vendor> findByLastName(String lastName);
}
