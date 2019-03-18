package com.rest.api.v1.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.rest.api.v1.models.CategoryDTO;
import com.rest.api.v1.services.CategoryService;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {

	@Mock
	CategoryService categoryService;
	@InjectMocks
	CategoryController categoryController;
	MockMvc mockMvc;
	
	// setup mockMvc to categoryController
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
	}
	
	@Test
	public void testListCategories() throws Exception {
		CategoryDTO cat1 = new CategoryDTO();
		cat1.setId(1L);
		cat1.setName("Mobiles");
		CategoryDTO cat2 = new CategoryDTO();
		cat2.setId(2L);
		cat2.setName("Laptops");
		
		List<CategoryDTO> categories = Arrays.asList(cat1, cat2);
		
		when(categoryService.getAllCategories()).thenReturn(categories);
		
		mockMvc.perform(get("/api/v1/categories/").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.categories", hasSize(2)));
	}
	
	@Test
	public void testGetCategoryByName() throws Exception{
		CategoryDTO cat = new CategoryDTO();
		cat.setId(1L);
		cat.setName("Mobiles");
		when(categoryService.getCategoryByName(anyString())).thenReturn(cat);
		
		mockMvc.perform(get("/api/v1/categories/Mobiles").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name", equalTo("Mobiles")));
	}
	
}
