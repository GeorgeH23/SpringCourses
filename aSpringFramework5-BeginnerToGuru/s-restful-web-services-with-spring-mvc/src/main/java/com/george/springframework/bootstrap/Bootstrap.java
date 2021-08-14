package com.george.springframework.bootstrap;

import com.george.springframework.domain.Category;
import com.george.springframework.domain.Customer;
import com.george.springframework.domain.Vendor;
import com.george.springframework.repositories.CategoryRepository;
import com.george.springframework.repositories.CustomerRepository;
import com.george.springframework.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        loadCategories();
        loadCustomers();
        loadVendors();
    }

    private void loadCategories() {
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
    }

    private void loadCustomers() {
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

    private void loadVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setName("Vendor 1");

        Vendor vendor2 = new Vendor();
        vendor2.setName("Vendor 2");

        vendorRepository.save(vendor1);
        vendorRepository.save(vendor2);

        log.debug("Vendors Loaded = " + vendorRepository.count());
    }
}
