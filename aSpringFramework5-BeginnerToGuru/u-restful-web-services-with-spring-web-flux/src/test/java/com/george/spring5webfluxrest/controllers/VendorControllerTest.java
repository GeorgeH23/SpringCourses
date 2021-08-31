package com.george.spring5webfluxrest.controllers;

import com.george.spring5webfluxrest.domain.Vendor;
import com.george.spring5webfluxrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertTrue;

class VendorControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController controller;

    @BeforeEach
    public void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        controller = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    void list() {

        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().firstName("Fred").lastName("Flintstone").build(),
                        Vendor.builder().firstName("Barney").lastName("Rubble").build()));

        webTestClient.get()
                .uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getById() {
        BDDMockito.given(vendorRepository.findById("someid"))
                .willReturn(Mono.just(Vendor.builder().firstName("Jimmy").lastName("Johns").build()));

        webTestClient.get()
                .uri("/api/v1/vendors/someid")
                .exchange()
                .expectStatus().isOk()
                .expectBody().consumeWith(str -> assertTrue(str.toString().contains("Jimmy")));
    }

    @Test
    void getByLastName() {
        BDDMockito.given(vendorRepository.findByLastName("Weston"))
                .willReturn(Mono.just(Vendor.builder().firstName("Michael").lastName("Weston").build()));

        webTestClient.get()
                .uri("/api/v1/vendors/name/Weston")
                .exchange()
                .expectStatus().isOk()
                .expectBody().consumeWith(str -> assertTrue(str.toString().contains("Weston")));
    }

}