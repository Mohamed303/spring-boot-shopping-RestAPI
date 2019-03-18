package com.rest.api.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rest.api.v1.models.ProductDTO;
import com.rest.api.v1.models.ProductListDTO;
import com.rest.api.v1.services.ProductService;

@Controller
@RequestMapping("/api/v1/products")
public class ProductController {

	@Autowired
	ProductService productService;
	
	@GetMapping
	public ResponseEntity<ProductListDTO> listProducts(){
		ProductListDTO products = new ProductListDTO(productService.getAllProducts());
		return new ResponseEntity<ProductListDTO>(products, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> GetProductById(@PathVariable Long id) {
		return new ResponseEntity<ProductDTO>(productService.getProductById(id) ,HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ProductDTO> createNewProduct(@RequestBody ProductDTO productDTO){
		return new ResponseEntity<ProductDTO>(productService.createNewProduct(productDTO), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){
		return new ResponseEntity<ProductDTO>(productService.saveProductByDTO(id, productDTO), HttpStatus.OK);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<ProductDTO> patchProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){
		ProductDTO updatedProduct = productService.patchProduct(id, productDTO);
		return new ResponseEntity<ProductDTO>(updatedProduct, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
		productService.deleteProductById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
