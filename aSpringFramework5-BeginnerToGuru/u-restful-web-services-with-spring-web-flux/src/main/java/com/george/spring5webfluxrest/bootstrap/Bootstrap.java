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
        log.debug("in run...");
        if (categoryRepository.count().block() == 0) {
            //load data
            log.debug("### LOADING DATA ON BOOTSTRAP ###");

            categoryRepository.save(Category.builder().description("Fruits").build());
            categoryRepository.save(Category.builder().description("Nuts").build());
            categoryRepository.save(Category.builder().description("Breads").build());
            categoryRepository.save(Category.builder().description("Meats").build());
            categoryRepository.save(Category.builder().description("Eggs").build());

            log.debug("Loaded Categories: " + categoryRepository.count().block());

            vendorRepository.save(Vendor.builder().firstName("George").lastName("Harpa").build());

            log.debug("Loaded Vendors: " + vendorRepository.count().block());
        }

    }
}
