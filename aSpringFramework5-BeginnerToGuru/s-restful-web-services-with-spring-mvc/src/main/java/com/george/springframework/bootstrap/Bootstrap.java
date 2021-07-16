package com.george.springframework.bootstrap;

import com.george.springframework.domain.Category;
import com.george.springframework.domain.Customer;
import com.george.springframework.repositories.CategoryRepository;
import com.george.springframework.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        log.debug("Categories Loaded = " + categoryRepository.count());

        Customer johnson = new Customer();
        johnson.setFirstName("Pamela");
        johnson.setLastName("Johnson");
        johnson.setCustomerUrl("johnson_pamela.com");

        Customer harper = new Customer();
        harper.setFirstName("George");
        harper.setLastName("Harper");
        harper.setCustomerUrl("george_harper.com");

        customerRepository.save(johnson);
        customerRepository.save(harper);

        log.debug("Customers Loaded = " + customerRepository.count());
    }
}
