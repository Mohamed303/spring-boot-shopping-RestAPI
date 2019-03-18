package com.rest.api.v1.bootstrap;

import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.rest.api.v1.domains.Category;
import com.rest.api.v1.domains.Customer;
import com.rest.api.v1.domains.Product;
import com.rest.api.v1.mapper.ProductMapper;
import com.rest.api.v1.models.ProductDTO;
import com.rest.api.v1.repositories.CategoryRepository;
import com.rest.api.v1.repositories.CustomerRepository;
import com.rest.api.v1.repositories.ProductRepository;
import com.rest.api.v1.services.ProductServiceImpl;

@Component
@Transactional
public class Bootstrap implements CommandLineRunner{

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	ProductRepository productRepository;

	@Override
	public void run(String... args) throws Exception {
		// adding dummy categories
		String[] categories = new String[] {"Mobiles", "Laptops", "Tablets", "Accessories", "PCs"};
		for (String cat : categories) {
			Category category = new Category();
			category.setName(cat);
			categoryRepository.save(category);
		}
		System.out.println("Categories Loaded : " + categoryRepository.count());
		
		// adding dummy customers
		Customer c1 = new Customer();
		c1.setFirstName("Roberto");
		c1.setLastName("Firmino");
		customerRepository.save(c1);
		Customer c2 = new Customer();
		c2.setFirstName("Sadio");
		c2.setLastName("Mane");
		customerRepository.save(c2);
		
		// add products and categories
		Category category1 = categoryRepository.findByName("Mobiles");
		Category category2 = categoryRepository.findByName("Laptops");
		
		Product product1 = new Product();
		product1.setName("Iphone 6");
		product1.setPrice(7500.0);
		product1.getCategories().add(category1);
		Product product2 = new Product();
		product2.setName("MacPro");
		product2.setPrice(27500.0);
		product2.getCategories().add(category2);	
		
		productRepository.save(product1);
		productRepository.save(product2);
		
		
	}
}
