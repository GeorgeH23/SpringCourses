package com.george.restapp.controller;

import com.george.restapp.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class UserController {

    private final ApiService apiService;

    public UserController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping({"", "/", "/index"})
    public String index(){
        return "index";
    }

    @PostMapping("/users")
    public Mono<String> formPost(Model model, ServerWebExchange serverWebExchange) {

        return serverWebExchange.getFormData()
                .map(formData -> {
                    Integer limit = Integer.parseInt(formData.get("limit").get(0));
                     model.addAttribute("users", apiService.getUsers(limit));
                     return "userlist"; })
                .doOnError(thr -> log.error("Error handling request"))
                .onErrorResume(WebExchangeBindException.class, thr -> Mono.just("/index"));

    }
}
