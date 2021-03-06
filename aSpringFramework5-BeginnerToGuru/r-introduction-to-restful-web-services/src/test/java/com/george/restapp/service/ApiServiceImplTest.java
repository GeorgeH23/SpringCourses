package com.george.restapp.service;

import com.george.restapp.api.domain.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class ApiServiceImplTest {

    @Autowired
    ApiService apiService;

    @BeforeEach
    void setUp() {
    }


    @Test
    void testGetUsers() {

        Flux<User> users = apiService.getUsers(3);

        assertEquals(3, users.collectList().block().size());
    }
}