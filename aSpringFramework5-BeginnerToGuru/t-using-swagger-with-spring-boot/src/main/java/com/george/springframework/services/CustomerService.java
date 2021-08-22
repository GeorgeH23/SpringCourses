package com.george.springframework.services;

import com.george.springframework.api.v1.model.CustomerDTO;
import com.george.springframework.api.v1.model.CustomerListDTO;


public interface CustomerService {

    CustomerListDTO getAllCustomers();

    CustomerDTO getCustomerByName(String name);

    CustomerDTO getCustomerById(Long id);

    CustomerDTO createNewCustomer(CustomerDTO customerDTO);

    CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO);

    CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO);

    void deleteCustomerById(Long id);
}
