package com.mycompany.main.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mycompany.main.entities.Customer;
import com.mycompany.main.repository.CustomerRepository;

@Service
public class CustomerServicesImpl implements CustomerServices {

	@Autowired
	CustomerRepository customerRepository;

	
	@Override
	public String addNewCustomer(Customer customer) {
		try {
			customerRepository.save(customer);
			return "success";
		}catch(Exception e) {
			e.printStackTrace();
			return "failure";
		}
	}


	@Override
	public List<Customer> allCustomers() {	
		return customerRepository.findAll();
	}


	@Override
	public Customer getCustomerById(int id) {
		Optional<Customer> optionalCustomer = customerRepository.findById(id);
		if(optionalCustomer.isPresent())
			return optionalCustomer.get();
		else
			return null;
	}


	@Override
	public String updateCustomerDetails(Customer customer) {
		try {
			customerRepository.save(customer);
			return "Customer Updated Successfully";
		}catch(Exception e) {
			e.printStackTrace();
			return "Updation failure";
		}
	}
	
}
