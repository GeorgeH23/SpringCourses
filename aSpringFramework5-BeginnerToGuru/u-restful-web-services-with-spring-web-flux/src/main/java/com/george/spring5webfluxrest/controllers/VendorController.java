package com.george.spring5webfluxrest.controllers;

import com.george.spring5webfluxrest.domain.Vendor;
import com.george.spring5webfluxrest.repositories.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/api/v1/vendors/name/{name}")
    public Mono<Vendor> getByLastName(@PathVariable String name) {
        return vendorRepository.findByLastName(name);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/vendors")
    public Mono<Void> create(@RequestBody Publisher<Vendor> vendorStream) {
        return vendorRepository.saveAll(vendorStream).then();
    }

    @PutMapping("/api/v1/vendors/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Vendor> updateVendor(@PathVariable String id, @RequestBody Vendor vendor) {
        return vendorRepository
                .findById(id)
                .flatMap(foundVendor -> {
                    vendor.setId(foundVendor.getId());
                    return vendorRepository.save(vendor);
                })
                .switchIfEmpty(Mono.error(new Exception("Vendor not found")));
    }

    @PatchMapping("/api/v1/vendors/{id}")
    public Mono<Vendor> patch(@PathVariable String id, @RequestBody Vendor vendor) {

        Mono<Vendor> foundVendor = vendorRepository.findById(id);

        return foundVendor
                .flatMap(vend -> {
                    boolean changed = false;

                    if (vend.getFirstName() != null && !vend.getFirstName().equals(vendor.getFirstName())) {
                        vend.setFirstName(vendor.getFirstName());
                        changed = true;
                    }
                    if (vend.getLastName() != null && !vend.getLastName().equals(vendor.getLastName())) {
                        vend.setLastName(vendor.getLastName());
                        changed = true;
                    }

                     if (changed) {
                         return vendorRepository.save(vend);
                     } else {
                         return Mono.just(vend);
                     }
                })
                .switchIfEmpty(foundVendor);

    }
}
