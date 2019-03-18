package com.rest.api.v1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rest.api.v1.domains.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
