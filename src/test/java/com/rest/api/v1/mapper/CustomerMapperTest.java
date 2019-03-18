package com.rest.api.v1.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.rest.api.v1.domains.Customer;
import com.rest.api.v1.models.CustomerDTO;

@RunWith(MockitoJUnitRunner.class)
public class CustomerMapperTest {

	CustomerMapper mapper = CustomerMapper.INSTANCE;
	
	@Test
	public void testCustomerToCustomerDTO () throws Exception {
		Customer c1 = new Customer();
		c1.setId(1L);
		c1.setFirstName("Roberto");
		c1.setLastName("Firmino");
		
		CustomerDTO c1DTO = mapper.customerToCustomerDTO(c1);
		
		assertEquals(c1.getLastName(), c1DTO.getLastName());
		assertEquals(c1.getFirstName(), c1DTO.getFirstName());
		assertEquals(c1.getId(), c1DTO.getId());
	}
	
}
