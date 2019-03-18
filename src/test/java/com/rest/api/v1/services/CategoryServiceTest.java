package com.rest.api.v1.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.rest.api.v1.domains.Category;
import com.rest.api.v1.mapper.CategoryMapper;
import com.rest.api.v1.models.CategoryDTO;
import com.rest.api.v1.repositories.CategoryRepository;
import com.rest.api.v1.services.CategoryService;
import com.rest.api.v1.services.CategoryServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

	private static final String NAME = "Fruits";
	private static final Long ID = 1L;

	@Mock
	CategoryRepository categoryRepo;
	
	CategoryService categoryService;
	
	
	@Before
	public void setUp() throws Exception{
		categoryService = new CategoryServiceImpl(categoryRepo, CategoryMapper.INSTANCE);
	}
	
	@Test
	public void getAllCategoriesTest() throws Exception {
		// given
		List<Category> categoryList = Arrays.asList(new Category(), new Category(), new Category());
		when(categoryRepo.findAll()).thenReturn(categoryList);
		// when
		List<CategoryDTO> categoriesDTOs = categoryService.getAllCategories();
		// then
		assertEquals(categoryList.size(), categoriesDTOs.size());
	}
	
	@Test
	public void testGetCategoryByName() throws Exception {
		// given
		Category category = new Category();
		category.setId(1L);
		category.setName(NAME);
		when(categoryRepo.findByName(anyString())).thenReturn(category);
		// when
		CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);
		// then
		assertEquals(ID, categoryDTO.getId());
		assertEquals(NAME, categoryDTO.getName());
	}
}
