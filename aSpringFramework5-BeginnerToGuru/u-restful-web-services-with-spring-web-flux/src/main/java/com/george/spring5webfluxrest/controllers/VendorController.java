package com.george.spring5webfluxrest.controllers;

import com.george.spring5webfluxrest.domain.Category;
import com.george.spring5webfluxrest.domain.Vendor;
import com.george.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VendorController {

    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping("/api/v1/vendors")
    public Flux<Vendor> listVendors() {
        return vendorRepository.findAll();
    }

    @GetMapping("/api/v1/vendors/{id}")
    public Mono<Vendor> getVendorById(@PathVariable String id) {
        return vendorRepository.findById(id);
    }

    @GetMapping("/api/v1/vendorsgetBy/name/{name}")
    public Mono<Vendor> getByLastName(@PathVariable String name) {
        return vendorRepository.findByLastName(name);
    }
}
