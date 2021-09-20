package com.george.springframework.api.v1.mapper;

import com.george.springframework.domain.Customer;
import com.george.springframework.model.CustomerDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerMapperTest {

    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Doe";
    public static final String URL = "John_Doe.com";
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    void customerToCustomerDTO() {
        //given
        Customer customer = new Customer();
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);
        customer.setCustomerUrl(URL);

        //when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        //then
        assertEquals(FIRST_NAME, customerDTO.getFirstName());
        assertEquals(LAST_NAME, customerDTO.getLastName());
        assertEquals(URL, customerDTO.getCustomerUrl());
    }
}