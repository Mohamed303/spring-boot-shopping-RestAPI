package com.rest.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.rest.api.v1.domains.Product;
import com.rest.api.v1.models.ProductDTO;

@Mapper
public interface ProductMapper {

	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
	
	ProductDTO productToProductDTO(Product product);
	
	Product productDTOToProduct(ProductDTO productDTO);
	
}
