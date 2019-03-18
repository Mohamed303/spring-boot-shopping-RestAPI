package com.rest.api.v1.models;

import java.util.HashSet;
import java.util.Set;

import com.rest.api.v1.domains.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
	private Long id;
	private String name;
	private double price;
	
	private Set<Category> categories = new HashSet<>();
}
