package com.george.spring5webfluxrest.bootstrap;

import com.george.spring5webfluxrest.domain.Category;
import com.george.spring5webfluxrest.domain.Vendor;
import com.george.spring5webfluxrest.repositories.CategoryRepository;
import com.george.spring5webfluxrest.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
        log.debug("in run...");
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //load data
        log.debug("### LOADING DATA ON BOOTSTRAP ###");

        loadCategories();
        loadVendors();
    }

    private void loadCategories() {
        categoryRepository.deleteAll()
                .thenMany(Flux.just("Fruits", "Nuts", "Breads", "Meats", "Eggs")
                        .map(name -> new Category(null, name))
                        .flatMap(categoryRepository::save))
                .then(categoryRepository.count())
                .subscribe(categories -> log.debug(categories + " categories saved"));
    }

    private void loadVendors() {
        vendorRepository.deleteAll()
                .thenMany(Flux.just(Vendor.builder().firstName("Joe").lastName("Buck").build(),
                                Vendor.builder().firstName("Michael").lastName("Weston").build(),
                                Vendor.builder().firstName("Jessie").lastName("Waters").build(),
                                Vendor.builder().firstName("Jimmy").lastName("Buffet").build())
                        .flatMap(vendorRepository::save))
                .then(vendorRepository.count())
                .subscribe(vendors -> log.debug(vendors + " vendors saved"));
    }
}
