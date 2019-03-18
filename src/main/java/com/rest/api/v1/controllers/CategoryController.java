package com.rest.api.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rest.api.v1.models.CategoryDTO;
import com.rest.api.v1.models.CategoryListDTO;
import com.rest.api.v1.services.CategoryService;

@Controller
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {

	public static final String BASE_URL = "/api/v1/categories";
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<CategoryListDTO> getAllCategories(){
		CategoryListDTO categoryList = new CategoryListDTO(categoryService.getAllCategories());
		return new ResponseEntity<CategoryListDTO> (categoryList, HttpStatus.OK);
	}
	
	@GetMapping("/{name}")
	public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name){
		CategoryDTO category = categoryService.getCategoryByName(name);
		return new ResponseEntity<CategoryDTO> (category, HttpStatus.OK);
	}
}
