package com.rest.api.v1.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.rest.api.v1.domains.Product;
import com.rest.api.v1.models.ProductDTO;

@RunWith(MockitoJUnitRunner.class)
public class ProductMapperTest {

	ProductMapper mapper = ProductMapper.INSTANCE;
	
	@Test
	public void testProductToProductDTO() throws Exception {
		Product product1 = new Product();
		product1.setId(1L);
		product1.setName("Iphone 6");
		product1.setPrice(15.2);
		
		ProductDTO product1DTO = mapper.productToProductDTO(product1);
		
		assertEquals(product1.getId(), product1DTO.getId());
		assertEquals(product1.getName(), product1DTO.getName());
		assertEquals(product1.getPrice(), product1DTO.getPrice(), 0.01);
	}
	
}
