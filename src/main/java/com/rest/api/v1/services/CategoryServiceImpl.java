package com.rest.api.v1.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rest.api.v1.mapper.CategoryMapper;
import com.rest.api.v1.models.CategoryDTO;
import com.rest.api.v1.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository categoryRepo;
	CategoryMapper mapper;
	
	
	public CategoryServiceImpl(CategoryRepository categoryRepo, CategoryMapper mapper) {
		this.categoryRepo = categoryRepo;
		this.mapper = mapper;
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		return categoryRepo.findAll()
				.stream()
				.map(mapper::categoryToCategoryDTO)
				.collect(Collectors.toList());
	}

	@Override
	public CategoryDTO getCategoryByName(String name) {
		return mapper.categoryToCategoryDTO(categoryRepo.findByName(name));
	}

}
