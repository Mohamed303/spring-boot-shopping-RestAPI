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

import com.rest.api.v1.domains.Product;
import com.rest.api.v1.mapper.ProductMapper;
import com.rest.api.v1.models.ProductDTO;
import com.rest.api.v1.repositories.ProductRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
	
	@Mock
	ProductRepository productRepo;
	
	ProductService productService;
	
	@Before
	public void setUp() {
		productService = new ProductServiceImpl(productRepo, ProductMapper.INSTANCE);
	}
	
	@Test
	public void getAllProductsTest() throws Exception {
		// given
		List<Product> products = Arrays.asList(new Product(), new Product());
		when(productRepo.findAll()).thenReturn(products);
		// when
		List<ProductDTO> productsList = productService.getAllProducts();
		// then
		assertEquals(2, productsList.size());
	}
	
	@Test
	public void getProductByIdTest() throws Exception {
		// given
		Product product = new Product();
		product.setId(1L);
		product.setName("MackPro");
		product.setPrice(27500.0);
		when(productRepo.findById(anyLong())).thenReturn(Optional.ofNullable(product));
		// when
		ProductDTO productDTO = productService.getProductById(1L);
		// then
		assertEquals("MackPro", productDTO.getName());
		assertEquals(27500.0, productDTO.getPrice(),0.001);
	}
	
	@Test
	public void createNewProductTest() throws Exception {
		// given
		ProductDTO product = new ProductDTO();
		product.setId(1L);
		product.setName("Samsung S9");
		product.setPrice(13000.0);
		Product savedProduct = new Product();
		savedProduct.setId(product.getId());
		savedProduct.setName(product.getName());
		savedProduct.setPrice(product.getPrice());
		when(productRepo.save(any(Product.class))).thenReturn(savedProduct);
		// when
		ProductDTO productDTO = productService.createNewProduct(product);
		// then
		assertEquals(product.getName(), productDTO.getName());
		assertEquals(product.getPrice(), productDTO.getPrice(), 0.001);
	}
	
	@Test
	public void saveProductByDTOTest() throws Exception {
		// given
		ProductDTO product = new ProductDTO();
		product.setId(1L);
		product.setName("Xiaomi Note5");
		product.setPrice(4000.0);
		Product savedProduct = new Product();
		savedProduct.setId(product.getId());
		savedProduct.setName(product.getName());
		savedProduct.setPrice(product.getPrice());
		when(productRepo.save(any(Product.class))).thenReturn(savedProduct);
		
		// when
		ProductDTO productDTO = productService.saveProductByDTO(1L, product);
		// then
		assertEquals(product.getId(), productDTO.getId());
		assertEquals("Xiaomi Note5", productDTO.getName());
		assertEquals(4000.0, productDTO.getPrice(), 0.001);
	}
	
	@Test
	public void patchProductTest() throws Exception {
		// given
		ProductDTO product = new ProductDTO();
		product.setName("Xiaomi Note5");
		Product savedProduct = new Product();
		savedProduct.setId(1L);
		savedProduct.setName(product.getName());
		savedProduct.setPrice(3900.0);
		when(productRepo.findById(anyLong())).thenReturn(Optional.of(savedProduct));
		when(productRepo.save(any(Product.class))).thenReturn(savedProduct);
		// when
		ProductDTO productDTO = productService.patchProduct(1L, product);
		// then
		assertEquals(Long.valueOf(1), productDTO.getId());
		assertEquals("Xiaomi Note5", productDTO.getName());
		assertEquals(3900.0, productDTO.getPrice(), 0.001);
	}
	
	@Test
	public void deleteProductTest() throws Exception{
		productRepo.deleteById(1L);
		verify(productRepo, times(1)).deleteById(anyLong());
	}
}
