package com.rest.api.v1.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rest.api.v1.models.CustomerDTO;
import com.rest.api.v1.services.CustomerService;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest extends AbsractRestControllerTest {

	@Mock
	CustomerService customerService;
	@InjectMocks
	CustomerController customerController;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}
	
	@Test
	public void testListCustomers() throws Exception {
		//given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstName("Sadio");
        customer1.setLastName("Mane");
        customer1.setCustomerUrl("/api/v1/customer/1");
        CustomerDTO customer2 = new CustomerDTO();
        customer2.setFirstName("Roberto");
        customer2.setLastName("Firmino");
        customer2.setCustomerUrl("/api/v1/customer/2");
        List<CustomerDTO> customers = Arrays.asList(customer1, customer2);
        
        // when
        when(customerService.getAllCustomers()).thenReturn(customers);
        
        // then
        mockMvc.perform(get("/api/v1/customers/").contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.customers", hasSize(2)));
	}
	
	@Test
	public void testGetCustomerById() throws Exception {
		//given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstName("Sadio");
        customer1.setLastName("Mane");
        customer1.setCustomerUrl("/api/v1/customer/1");
        // when
        when(customerService.getCustomerById(anyLong())).thenReturn(customer1);
        // then
        mockMvc.perform(get("/api/v1/customers/1").contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.firstName", equalTo("Sadio")));
	}
	
	@Test
	public void testCreateNewCustomer() throws Exception {
		// given
		CustomerDTO customer = new CustomerDTO();
		customer.setId(1L);
		customer.setFirstName("Sadio");
		customer.setLastName("Mane");
		
		CustomerDTO returnedCustomer = new CustomerDTO();
		returnedCustomer.setId(customer.getId());
		returnedCustomer.setFirstName(customer.getFirstName());
		returnedCustomer.setLastName(customer.getLastName());
		returnedCustomer.setCustomerUrl("/api/v1/customers/1");
		
		//when
		when(customerService.createNewCustomer(customer)).thenReturn(returnedCustomer);
		
		//then
		mockMvc.perform(post("/api/v1/customers/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customer)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.firstName", equalTo("Sadio")))
			.andExpect(jsonPath("$.customerUrl", equalTo("/api/v1/customers/1")));
	}
	
	@Test
	public void testUpdateCustomer() throws Exception {
		// given
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstName("James");
		customerDTO.setLastName("Milner");
		
		CustomerDTO returnedDTO = new CustomerDTO();
		returnedDTO.setFirstName(customerDTO.getFirstName());
		returnedDTO.setLastName(customerDTO.getLastName());
		returnedDTO.setCustomerUrl("/api/v1/customers/1");
		
		// when
		when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnedDTO);
		
		// then
		mockMvc.perform(put("/api/v1/customers/1")
			.content(asJsonString(customerDTO))
			.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.firstName", equalTo(returnedDTO.getFirstName())))
		.andExpect(jsonPath("$.lastName", equalTo(returnedDTO.getLastName())))
		.andExpect(jsonPath("$.customerUrl", equalTo(returnedDTO.getCustomerUrl())));
	}
	
	@Test
	public void testPatchCustomer() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstName("Jordan");
		
		CustomerDTO returnedDTO = new CustomerDTO();
		returnedDTO.setFirstName(customerDTO.getFirstName());
		returnedDTO.setLastName("Henderson");
		returnedDTO.setCustomerUrl("/api/v1/customers/1");
		
		// when
		when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnedDTO);
		
		// then
		mockMvc.perform(patch("/api/v1/customers/1")
			.content(asJsonString(customerDTO))
			.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.firstName", equalTo("Jordan")))
		.andExpect(jsonPath("$.lastName", equalTo("Henderson")))
		.andExpect(jsonPath("$.customerUrl", equalTo("/api/v1/customers/1")));
	}
	
	@Test
	public void testDeleteCustomer() throws Exception {
		mockMvc.perform(delete("/api/v1/customers/1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		verify(customerService).deleteCustomerById(anyLong());
	}
}


