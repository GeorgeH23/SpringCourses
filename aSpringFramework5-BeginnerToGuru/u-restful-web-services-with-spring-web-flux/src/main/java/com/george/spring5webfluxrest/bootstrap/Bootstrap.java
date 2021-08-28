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

        if (categoryRepository.count().block() == 0) {
            //load data
            log.debug("### LOADING DATA ON BOOTSTRAP ###");

            loadCategories();
        }

        if (vendorRepository.count().block() == 0) {
            loadVendors();
        }
    }

    private void loadCategories() {
        categoryRepository.save(Category.builder().description("Fruits").build()).block();
        categoryRepository.save(Category.builder().description("Nuts").build()).block();
        categoryRepository.save(Category.builder().description("Breads").build()).block();
        categoryRepository.save(Category.builder().description("Meats").build()).block();
        categoryRepository.save(Category.builder().description("Eggs").build()).block();

        log.debug("Loaded Categories: " + categoryRepository.count().block());
    }

    private void loadVendors() {
        vendorRepository.save(Vendor.builder().firstName("George").lastName("Harpa").build()).block();
        vendorRepository.save(Vendor.builder().firstName("Joe").lastName("Buck").build()).block();
        vendorRepository.save(Vendor.builder().firstName("Micheal").lastName("Weston").build()).block();
        vendorRepository.save(Vendor.builder().firstName("Jessie").lastName("Waters").build()).block();
        vendorRepository.save(Vendor.builder().firstName("Bill").lastName("Nershi").build()).block();
        vendorRepository.save(Vendor.builder().firstName("Jimmy").lastName("Buffett").build()).block();

        log.debug("Loaded Vendors: " + vendorRepository.count().block());
    }
}
