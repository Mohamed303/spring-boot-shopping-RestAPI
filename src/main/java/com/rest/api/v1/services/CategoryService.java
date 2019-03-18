package com.rest.api.v1.services;

import java.util.List;

import com.rest.api.v1.models.CategoryDTO;

public interface CategoryService {

	List<CategoryDTO> getAllCategories();
	
	CategoryDTO getCategoryByName(String name);
}
