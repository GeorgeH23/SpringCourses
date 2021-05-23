package com.george.restapp.service;

import com.george.restapp.api.domain.User;
import reactor.core.publisher.Flux;

public interface ApiService {

    Flux<User> getUsers(Integer limit);
}
