package com.mycompany.main.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.main.entities.Admin;
import com.mycompany.main.repository.AdminRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class AdminServicesImpl implements AdminServices {

	@Autowired
	AdminRepository adminRepository;

	@Override
	public String findByEmail(String email, String password, HttpSession session) {
		try {
			Admin admin = adminRepository.findByEmail(email);
			if (admin != null) {
				if (admin.getPassword().equals(password)) {
					session.setAttribute("adminName", admin.getName());
					session.setAttribute("adminEmail", admin.getEmail());
					session.setAttribute("adminId", admin.getId());
					session.setAttribute("adminPhone", admin.getPhone());
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
	public List<Admin> allAdmin() {
		return adminRepository.findAll();
	}
}
