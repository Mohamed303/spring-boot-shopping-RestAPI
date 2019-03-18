package com.rest.api.v1.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryListDTO {

	List<CategoryDTO> categories;
}
