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

import static com.george.springframework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        mockMvc.perform(get(CustomerController.BASE_URL)
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

        mockMvc.perform(get(CustomerController.BASE_URL + "name/John")
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

        mockMvc.perform(get(CustomerController.BASE_URL + "id/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME_C1)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME_C1)))
                .andExpect(jsonPath("$.customerUrl", equalTo(URL_C1)));
    }

    @Test
    void testCreateNewCustomer() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Sherlock");
        customerDTO.setLastName("Holmes");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customerDTO.getFirstName());
        returnDTO.setLastName(customerDTO.getLastName());
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "id/1");

        when(customerService.createNewCustomer(customerDTO)).thenReturn(returnDTO);

        // This can be used for debug purposes
        /*String response = mockMvc.perform(post(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andReturn().getResponse().getContentAsString();*/

        mockMvc.perform(post(CustomerController.BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo("Sherlock")))
                .andExpect(jsonPath("$.lastName", equalTo("Holmes")))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL + "id/1")));

    }

    @Test
    void testUpdateCustomer() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Fred");
        customer.setLastName("Flintstone");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName(customer.getLastName());
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "id/1");

        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(put(CustomerController.BASE_URL + "id/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Fred")))
                .andExpect(jsonPath("$.lastName", equalTo("Flintstone")))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL + "id/1")));
    }

    @Test
    void testPatchCustomer() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Tom");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName("Holland");
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "id/1");

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(patch(CustomerController.BASE_URL + "id/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Tom")))
                .andExpect(jsonPath("$.lastName", equalTo("Holland")))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL + "id/1")));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        mockMvc.perform(delete(CustomerController.BASE_URL + "id/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(anyLong());
    }
}






























































