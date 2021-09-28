package com.george.springframework.controllers.v1;

import com.george.springframework.model.CustomerDTO;
import com.george.springframework.model.CustomerListDTO;
import com.george.springframework.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(description = "This is my Customer Controller")
@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    public static final String BASE_URL = "/api/v1/customers/";

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "Get a list of customers.", notes = "Those are some notes about the API.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerListDTO getAllCustomers() {

        CustomerListDTO customerListDTO = new CustomerListDTO();
        customerListDTO.getCustomers().addAll(customerService.getAllCustomers());
        return customerListDTO;
    }

    @ApiOperation(value = "Get a customer by name.", notes = "Those are some notes about the API.")
    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomerByName(@PathVariable String name) {

        return customerService.getCustomerByName(name);
    }

    @ApiOperation(value = "Get a customer by id.", notes = "Those are some notes about the API.")
    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomerById(@PathVariable Long id) {

        return customerService.getCustomerById(id);
    }

    @ApiOperation(value = "Create a new customer.", notes = "Those are some notes about the API.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO) {

        return customerService.createNewCustomer(customerDTO);
    }

    @ApiOperation(value = "Update an existing customer.", notes = "Those are some notes about the API.")
    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {

        return customerService.saveCustomerByDTO(id, customerDTO);
    }

    @ApiOperation(value = "Update a customer property.", notes = "Those are some notes about the API.")
    @PatchMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {

        return customerService.patchCustomer(id, customerDTO);
    }

    @ApiOperation(value = "Delete a customer.", notes = "Those are some notes about the API.")
    @DeleteMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable Long id) {

        customerService.deleteCustomerById(id);
    }

}
