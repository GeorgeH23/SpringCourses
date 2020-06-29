package com.george.dependencyinjectionwithspring.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("ES")
@Service("i18nService")
public class I18nSpanishGreetingController implements GreetingService {

    @Override
    public String sayGreeting() {
        return "Hola Mundo - Espagnol";
    }
}