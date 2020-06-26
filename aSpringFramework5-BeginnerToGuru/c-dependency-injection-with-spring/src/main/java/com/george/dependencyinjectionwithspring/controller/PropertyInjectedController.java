package com.george.dependencyinjectionwithspring.controller;

import com.george.dependencyinjectionwithspring.services.GreetingService;

public class PropertyInjectedController {

    public GreetingService greetingService;

    public String getGreeting() {
        return greetingService.sayGreeting();
    }
}
