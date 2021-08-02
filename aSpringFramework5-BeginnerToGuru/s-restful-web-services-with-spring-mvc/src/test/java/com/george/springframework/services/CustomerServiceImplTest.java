package com.george.springframework.services;

import com.george.springframework.api.v1.mapper.CustomerMapper;
import com.george.springframework.api.v1.model.CustomerDTO;
import com.george.springframework.domain.Customer;
import com.george.springframework.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    public static final Long ID = 2L;
    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Doe";
    public static final String URL = "John_Doe.com";
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    void getAllCustomers() {

        //given
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

        when(customerRepository.findAll()).thenReturn(customers);

        //when
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        //then
        assertEquals(3, customerDTOS.size());
    }

    @Test
    void getCustomerByName() {

        //given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);
        customer.setCustomerUrl(URL);

        when(customerRepository.findByLastName(anyString())).thenReturn(customer);

        //when
        CustomerDTO customerDTO = customerService.getCustomerByName(LAST_NAME);

        //then
        assertEquals(ID, customerDTO.getId());
        assertEquals(FIRST_NAME, customerDTO.getFirstName());
        assertEquals(LAST_NAME, customerDTO.getLastName());
        assertEquals(URL, customerDTO.getCustomerUrl());

    }

    @Test
    void createNewCustomer() {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Sherlock");
        customerDTO.setLastName("Holmes");
        customerDTO.setCustomerUrl("/api/v1/customer/id/1");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());
        savedCustomer.setCustomerUrl(customerDTO.getCustomerUrl());
        savedCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);

        //then
        assertEquals(customerDTO.getFirstName(), savedCustomer.getFirstName());
        assertEquals(customerDTO.getLastName(), savedCustomer.getLastName());
        assertEquals("/api/v1/customer/id/1", savedDto.getCustomerUrl());
    }

    @Test
    void saveCustomerByDTO() {

        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Jim");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());
        savedCustomer.setCustomerUrl("/api/v1/customer/id/1");
        savedCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDto = customerService.saveCustomerByDTO(1L, customerDTO);

        //then
        assertEquals(customerDTO.getFirstName(), savedDto.getFirstName());
        assertEquals(customerDTO.getLastName(), savedDto.getLastName());
        assertEquals("/api/v1/customer/id/1", savedDto.getCustomerUrl());
    }

    @Test
    void deleteCustomerById() {
        Long id = 1L;
        customerRepository.deleteById(id);
        verify(customerRepository, times(1)).deleteById(anyLong());
    }
}
























