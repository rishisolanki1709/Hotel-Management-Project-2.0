package com.mycompany.main.services;

import java.util.List;
import java.util.Optional;

import com.mycompany.main.entities.Employee;

import jakarta.servlet.http.HttpSession;

public interface EmployeeServices {
	String findByEmail(String email, String password, HttpSession session);
	List<Employee> allEmployees();
	String addNewEmployee(Employee employee);
	void removeEmployee(int id);
	Optional<Employee> findById(int id);
	String updateEmployeeDetail(Employee employee);
}
