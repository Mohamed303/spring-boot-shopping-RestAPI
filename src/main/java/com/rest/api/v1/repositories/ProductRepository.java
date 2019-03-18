package com.rest.api.v1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rest.api.v1.domains.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
