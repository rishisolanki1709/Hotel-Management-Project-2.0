package com.mycompany.main.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.main.entities.Employee;
import com.mycompany.main.repository.EmployeeRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class EmployeeServicesImpl implements EmployeeServices {

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public String findByEmail(String email, String password, HttpSession session) {
		try {
			Employee employee = employeeRepository.findByEmail(email);
			if (employee != null) {
				if (employee.getPassword().equals(password))
				{
					session.setAttribute("employeeName", employee.getName());
					return "success";
				}
				else
					return "Invalid Password";
			} else {
				return "Email Not Found";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "somthing went wrong";
		}
	}

	@Override
	public List<Employee> allEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public String addNewEmployee(Employee employee) {
		try {
			employeeRepository.save(employee);
			return "success";
		}catch(Exception e) {
			e.printStackTrace();
			return "failure";
		}
	}

	@Override
	public void removeEmployee(int id) {
		employeeRepository.deleteById(id);
	}

	@Override
	public Optional<Employee> findById(int id) {
		return employeeRepository.findById(id);
	}

	@Override
	public String updateEmployeeDetail(Employee employee) {
		try {
			employeeRepository.save(employee);
			return "Employee Updated Successfully";
		}catch(Exception e) {
			e.printStackTrace();
			return "Updation failure";
		}
	}
	
}
