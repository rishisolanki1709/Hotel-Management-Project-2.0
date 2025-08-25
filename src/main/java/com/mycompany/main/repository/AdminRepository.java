package com.mycompany.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.main.entities.Admin;



public interface AdminRepository extends JpaRepository<Admin, Integer> {
	Admin findByEmail(String email);
}
