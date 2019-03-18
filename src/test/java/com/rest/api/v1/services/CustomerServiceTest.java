package com.rest.api.v1.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.rest.api.v1.domains.Customer;
import com.rest.api.v1.mapper.CustomerMapper;
import com.rest.api.v1.models.CustomerDTO;
import com.rest.api.v1.repositories.CustomerRepository;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

	@Mock
	CustomerRepository customerRepo;
	
	CustomerService customerService;
	
	@Before
	public void setUp() {
		customerService = new CustomerServiceImpl(customerRepo, CustomerMapper.INSTANCE);
	}
	
	@Test
	public void getAllCustomersTest() throws Exception {
		// given
		List<Customer> customersList = Arrays.asList(new Customer(), new Customer());
		when(customerRepo.findAll()).thenReturn(customersList);
		// when
		List<CustomerDTO> customersDTOList = customerService.getAllCustomers();
		// then
		assertEquals(customersList.size(), customersDTOList.size());
	}
	
	@Test
	public void getCustomerByNameTest() throws Exception {
		// given
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setFirstName("Sadio");
		customer.setLastName("Mane");
		when(customerRepo.findById(anyLong())).thenReturn(Optional.ofNullable(customer));
		// when
		CustomerDTO customerDTO = customerService.getCustomerById(1L);
		// then
		assertEquals(customer.getFirstName(), customerDTO.getFirstName());
	}
	
	@Test
	public void createNewCustomerTest() throws Exception {
		// given
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setId(1L);
		customerDTO.setFirstName("Sadio");
		customerDTO.setLastName("Mane");
		
		Customer savedCustomer = new Customer();
		savedCustomer.setId(customerDTO.getId());
		savedCustomer.setFirstName(customerDTO.getFirstName());
		savedCustomer.setLastName(customerDTO.getLastName());
		
		when(customerRepo.save(any(Customer.class))).thenReturn(savedCustomer);
		// when
		CustomerDTO savedDTO = customerService.createNewCustomer(customerDTO);
		// then
		assertEquals(customerDTO.getFirstName(), savedDTO.getFirstName());
		assertEquals("/api/v1/customers/1", savedDTO.getCustomerUrl());
	}
	
	@Test
	public void saveCustomerByDTO() throws Exception {
		// given
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstName("James");
		customerDTO.setLastName("Milner");
		Customer savedCustomer = new Customer();
		savedCustomer.setFirstName(customerDTO.getFirstName());
		savedCustomer.setLastName(customerDTO.getLastName());
		savedCustomer.setId(1L);
		
		when(customerRepo.save(any(Customer.class))).thenReturn(savedCustomer);
		
		// when
		CustomerDTO savedDTO = customerService.saveCustomerByDTO(1L, customerDTO);
		
		// then
		assertEquals(customerDTO.getFirstName(), savedDTO.getFirstName());
		assertEquals("Milner", savedDTO.getLastName());
		assertEquals("/api/v1/customers/1", savedDTO.getCustomerUrl());
	}
	
	@Test
	public void deleteCustomerById() throws Exception {
		customerRepo.deleteById(1L);
		verify(customerRepo, times(1)).deleteById(anyLong());
	}
}


