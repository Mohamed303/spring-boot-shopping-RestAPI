package com.rest.api.v1.services;

import java.util.List;

import com.rest.api.v1.models.CustomerDTO;

public interface CustomerService {

	List<CustomerDTO> getAllCustomers();
	
	CustomerDTO getCustomerById(Long id);
	
	CustomerDTO createNewCustomer(CustomerDTO customerDTO);
	
	CustomerDTO saveCustomerByDTO (Long id, CustomerDTO customerDTO);
	
	CustomerDTO patchCustomer (Long id, CustomerDTO customerDTO);
	
	void deleteCustomerById(Long id);
}
