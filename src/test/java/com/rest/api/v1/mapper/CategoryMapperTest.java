package com.rest.api.v1.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.rest.api.v1.domains.Category;
import com.rest.api.v1.mapper.CategoryMapper;
import com.rest.api.v1.models.CategoryDTO;


public class CategoryMapperTest {

	private static final long ID = 1L;
	private static final String NAME = "Joe";
	CategoryMapper categoryMapper = CategoryMapper.INSTANCE;
	
	@Test
	public void testCategoryToCategoryDTO() throws Exception{
		// given 
		Category category = new Category();
		category.setName(NAME);
		category.setId(ID);
		
		// when
		CategoryDTO categoryDTO = CategoryMapper.INSTANCE.categoryToCategoryDTO(category);
		
		// then
		assertEquals(Long.valueOf(1L), categoryDTO.getId());
		assertEquals(NAME, categoryDTO.getName());
	}
}
