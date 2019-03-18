package com.rest.api.v1.services;

import java.util.List;

import com.rest.api.v1.models.ProductDTO;

public interface ProductService {
	
	List<ProductDTO> getAllProducts();

	ProductDTO getProductById(Long id);

	ProductDTO createNewProduct(ProductDTO productDTO);

	ProductDTO saveProductByDTO(Long id, ProductDTO productDTO);

	ProductDTO patchProduct(Long id, ProductDTO productDTO);

	void deleteProductById(Long id);
	
}
