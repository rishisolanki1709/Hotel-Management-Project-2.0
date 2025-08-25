package com.mycompany.main.services;


import java.util.List;

import com.mycompany.main.entities.Admin;

import jakarta.servlet.http.HttpSession;

public interface AdminServices {

	String findByEmail(String email, String password, HttpSession session);
	List<Admin> allAdmin();
}
