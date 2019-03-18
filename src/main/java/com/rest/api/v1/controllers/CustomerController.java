package com.rest.api.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rest.api.v1.models.CustomerDTO;
import com.rest.api.v1.models.CustomerListDTO;
import com.rest.api.v1.services.CustomerService;

@Controller
@RequestMapping("/api/v1/customers")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping
	public ResponseEntity<CustomerListDTO> listCustomers(){
		CustomerListDTO customers = new CustomerListDTO(customerService.getAllCustomers());
		return new ResponseEntity<CustomerListDTO>(customers, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
		CustomerDTO customer = customerService.getCustomerById(id);
		return new ResponseEntity<CustomerDTO>(customer, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO){
		CustomerDTO returnedCustomer = customerService.createNewCustomer(customerDTO);
		return new ResponseEntity<CustomerDTO>(returnedCustomer, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
		CustomerDTO updatedCustomer = customerService.saveCustomerByDTO(id, customerDTO);
		return new ResponseEntity<CustomerDTO>(updatedCustomer, HttpStatus.OK);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
		CustomerDTO updatedCustomer = customerService.patchCustomer(id, customerDTO);
		return new ResponseEntity<CustomerDTO>(updatedCustomer, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
		customerService.deleteCustomerById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
