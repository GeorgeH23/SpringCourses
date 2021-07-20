package com.george.springframework.controllers.v1;

import com.george.springframework.api.v1.model.CustomerDTO;
import com.george.springframework.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest {

    public static final Long ID_C1 = 2L;
    public static final String FIRST_NAME_C1 = "John";
    public static final String LAST_NAME_C1 = "Doe";
    public static final String URL_C1 = "John_Doe.com";

    public static final Long ID_C2 = 3L;
    public static final String FIRST_NAME_C2 = "Jane";
    public static final String LAST_NAME_C2 = "Harper";
    public static final String URL_C2 = "Jane_Harper.com";

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void testGetAllCustomers() throws Exception {
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setId(ID_C1);
        customer1.setFirstName(FIRST_NAME_C1);
        customer1.setLastName(LAST_NAME_C1);
        customer1.setCustomerUrl(URL_C1);

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setId(ID_C2);
        customer2.setFirstName(FIRST_NAME_C2);
        customer2.setLastName(LAST_NAME_C2);
        customer2.setCustomerUrl(URL_C2);

        List<CustomerDTO> customers = Arrays.asList(customer1, customer2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    void testGetCustomerByName() throws Exception {
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setId(ID_C1);
        customer1.setFirstName(FIRST_NAME_C1);
        customer1.setLastName(LAST_NAME_C1);
        customer1.setCustomerUrl(URL_C1);

        when(customerService.getCustomerByName(anyString())).thenReturn(customer1);

        mockMvc.perform(get("/api/v1/customers/name/John")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME_C1)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME_C1)))
                .andExpect(jsonPath("$.customerUrl", equalTo(URL_C1)));
    }

    @Test
    void testGetCustomerById() throws Exception {
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setId(ID_C1);
        customer1.setFirstName(FIRST_NAME_C1);
        customer1.setLastName(LAST_NAME_C1);
        customer1.setCustomerUrl(URL_C1);

        when(customerService.getCustomerById(anyLong())).thenReturn(customer1);

        mockMvc.perform(get("/api/v1/customers/id/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME_C1)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME_C1)))
                .andExpect(jsonPath("$.customerUrl", equalTo(URL_C1)));
    }
}






























































