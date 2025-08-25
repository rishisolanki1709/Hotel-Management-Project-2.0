package com.mycompany.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.main.entities.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	Employee findByEmail(String email);
}
