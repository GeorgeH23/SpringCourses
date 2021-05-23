package com.george.restapp.service;

import com.george.restapp.api.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Flux;

@Service
public class ApiServiceImpl implements ApiService {

    private final String apiUrl;

    public ApiServiceImpl(@Value("${api.url}") String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Override
    public Flux<User> getUsers(Integer limit) {
        if (limit <= 0) {
            limit = 1;
        }

        return WebClient.create(apiUrl + limit)
                .get()
                .uri(UriBuilder::build)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(resp -> resp.bodyToFlux(User.class));
    }
}
