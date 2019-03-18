package com.rest.api.v1.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
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

import com.rest.api.v1.models.ProductDTO;
import com.rest.api.v1.services.ProductService;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest extends AbsractRestControllerTest {

	@Mock
	ProductService productService;
	@InjectMocks
	ProductController productController;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}
	
	
	@Test
	public void testListProducts() throws Exception {
		// given
		ProductDTO product1 = new ProductDTO();
		product1.setId(1L);
		product1.setName("Lenovo Ideapad 500");
		product1.setPrice(12000);
		ProductDTO product2 = new ProductDTO();
		product2.setId(2L);
		product2.setName("Mackbook Pro 2018");
		product2.setPrice(35000);
		List<ProductDTO> products = Arrays.asList(product1, product2);
		//when
		when(productService.getAllProducts()).thenReturn(products);
		// then
		mockMvc.perform(get("/api/v1/products").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.products", hasSize(2)));
	}
	
	@Test
	public void testGetProductById() throws Exception {
		// given
		ProductDTO product = new ProductDTO();
		product.setId(1L);
		product.setName("IPhone 6");
		product.setPrice(7500.5);
		// when
		when(productService.getProductById(anyLong())).thenReturn(product);
		// then
		mockMvc.perform(get("/api/v1/products/1").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", equalTo(1)));
		
	}
	
	@Test
	public void testCreateNewProduct() throws Exception {
		// given
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(1L);
		productDTO.setName("Macbook Pro");
		productDTO.setPrice(40000.0);
		ProductDTO returnedProductDTO = new ProductDTO();
		returnedProductDTO.setId(productDTO.getId());
		returnedProductDTO.setName(productDTO.getName());
		returnedProductDTO.setPrice(productDTO.getPrice());
		// when
		when(productService.createNewProduct(any(ProductDTO.class))).thenReturn(returnedProductDTO);
		// then
		mockMvc.perform(post("/api/v1/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(productDTO)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.name", equalTo(productDTO.getName())))
			.andExpect(jsonPath("$.price", equalTo(productDTO.getPrice())));
	}

	@Test
	public void testUpdateProduct() throws Exception {
		// given
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(1L);
		productDTO.setName("Macbook Pro");
		productDTO.setPrice(40000.0);
		
		ProductDTO returnedProductDTO = new ProductDTO();
		returnedProductDTO.setId(productDTO.getId());
		returnedProductDTO.setName(productDTO.getName());
		returnedProductDTO.setPrice(productDTO.getPrice());
		
		// when
		when(productService.saveProductByDTO(anyLong(), any(ProductDTO.class))).thenReturn(returnedProductDTO);
		
		// then
		mockMvc.perform(put("/api/v1/products/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(productDTO)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", equalTo(productDTO.getName())))
			.andExpect(jsonPath("$.price", equalTo(productDTO.getPrice())));
	}
	
	@Test
	public void testPatchProduct() throws Exception {
		// given
		ProductDTO productDTO = new ProductDTO();
		productDTO.setName("Macbook Pro");
		
		ProductDTO returnedProductDTO = new ProductDTO();
		returnedProductDTO.setName(productDTO.getName());
		returnedProductDTO.setPrice(40000.0);
		
		// when
		when(productService.patchProduct(anyLong(), any(ProductDTO.class))).thenReturn(returnedProductDTO);
		
		// then
		mockMvc.perform(patch("/api/v1/products/1")
			.content(asJsonString(productDTO))
			.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name", equalTo("Macbook Pro")))
		.andExpect(jsonPath("$.price", equalTo(40000.0)));
	}
	
	@Test
	public void testDeleteProduct() throws Exception {
		mockMvc.perform(delete("/api/v1/products/1").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		verify(productService, times(1)).deleteProductById(anyLong());
	}
}





