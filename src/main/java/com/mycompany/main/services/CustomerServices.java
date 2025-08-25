package com.mycompany.main.services;

import java.util.List;

import com.mycompany.main.entities.Customer;


public interface CustomerServices {
	String addNewCustomer(Customer customer);
	List<Customer> allCustomers();
	Customer getCustomerById(int id);
	String updateCustomerDetails(Customer customer);
}
