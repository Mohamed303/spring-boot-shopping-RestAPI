package com.rest.api.v1.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rest.api.v1.domains.Product;
import com.rest.api.v1.mapper.ProductMapper;
import com.rest.api.v1.models.ProductDTO;
import com.rest.api.v1.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	private ProductRepository productRepo;
	private ProductMapper mapper;

	public ProductServiceImpl(ProductRepository productRepo, ProductMapper mapper) {
		this.productRepo = productRepo;
		this.mapper = mapper;
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		return productRepo.findAll()
				.stream()
				.map(product -> {
					ProductDTO productDTO = mapper.productToProductDTO(product);
					return productDTO;
				}).collect(Collectors.toList());
	}

	@Override
	public ProductDTO getProductById(Long id) {
		return productRepo.findById(id)
				.map(mapper::productToProductDTO)	
				.orElseThrow(RuntimeException::new);
	}
	
	private ProductDTO saveAndReturnDTO (Product product) {
		Product savedProduct = productRepo.save(product);
		return mapper.productToProductDTO(savedProduct);
	}

	@Override
	public ProductDTO createNewProduct(ProductDTO productDTO) {
		return saveAndReturnDTO(mapper.productDTOToProduct(productDTO));
	}

	@Override
	public ProductDTO saveProductByDTO(Long id, ProductDTO productDTO) {
		Product product = mapper.productDTOToProduct(productDTO);
		product.setId(id);
		return saveAndReturnDTO(product);
	}

	@Override
	public ProductDTO patchProduct(Long id, ProductDTO productDTO) {
		return productRepo.findById(id).map(product -> {
			if(productDTO.getName() != null)
				product.setName(productDTO.getName());
			if(productDTO.getPrice() != 0)
				product.setPrice(productDTO.getPrice());
			if(productDTO.getCategories() != null)
				product.setCategories(productDTO.getCategories());
			
			return mapper.productToProductDTO(productRepo.save(product));
		}).orElseThrow(RuntimeException::new);
	}

	@Override
	public void deleteProductById(Long id) {
		productRepo.deleteById(id);
	}

}
