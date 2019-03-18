package com.rest.api.v1.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rest.api.v1.domains.Customer;
import com.rest.api.v1.mapper.CustomerMapper;
import com.rest.api.v1.models.CustomerDTO;
import com.rest.api.v1.repositories.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepo;
	private CustomerMapper mapper;
	private static final String BASE_URL = "/api/v1/customers/";

	
	// using constructor to inject from test class !
	public CustomerServiceImpl(CustomerRepository customerRepo, CustomerMapper mapper) {
		this.customerRepo = customerRepo;
		this.mapper = mapper;
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		return customerRepo.findAll()
				.stream()
				.map(customer -> {
					CustomerDTO customerDTO = mapper.customerToCustomerDTO(customer);
					customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
					return customerDTO;
				})
				.collect(Collectors.toList());
	}

	@Override
	public CustomerDTO getCustomerById(Long id) {
		return customerRepo.findById(id)
        .map(mapper::customerToCustomerDTO)
        .orElseThrow(RuntimeException::new); //todo implement better exception handling
	}
	
	private CustomerDTO saveAndReturnDTO (Customer customer) {
		Customer savedCustomer = customerRepo.save(customer);
		CustomerDTO savedCustomerDTO = mapper.customerToCustomerDTO(savedCustomer);
		savedCustomerDTO.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));
		return savedCustomerDTO;
	}

	@Override
	public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
		return saveAndReturnDTO(mapper.customerDTOToCustomer(customerDTO));
	}

	@Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
		Customer customer = mapper.customerDTOToCustomer(customerDTO);
		customer.setId(id);
		return saveAndReturnDTO(customer);
    }

	@Override
	public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
		return customerRepo.findById(id).map(customer -> {
			if(customerDTO.getFirstName() != null)
				customer.setFirstName(customerDTO.getFirstName());
			if(customerDTO.getLastName() != null)
				customer.setLastName(customerDTO.getLastName());

			CustomerDTO returnDto = mapper.customerToCustomerDTO(customerRepo.save(customer));
			returnDto.setCustomerUrl(getCustomerUrl(id));
			return returnDto;
		}).orElseThrow(RuntimeException::new);
	}

	@Override
	public void deleteCustomerById(Long id) {
		customerRepo.deleteById(id);
	}
	
	private String getCustomerUrl(Long id) {
		return CustomerServiceImpl.BASE_URL  + id;
	}

}



