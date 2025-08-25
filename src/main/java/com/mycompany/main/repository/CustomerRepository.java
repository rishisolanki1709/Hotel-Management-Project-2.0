package com.mycompany.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.main.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
}
