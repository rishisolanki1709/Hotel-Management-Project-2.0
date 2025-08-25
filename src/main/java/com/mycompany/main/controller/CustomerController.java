package com.mycompany.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.main.entities.Room;
import com.mycompany.main.services.CustomerServices;
import com.mycompany.main.services.RoomServices;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	RoomServices roomServices;

	@Autowired
	CustomerServices customerServices;

	@GetMapping("/home")
	public String customerHome() {
		return "customer/home";
	}

	@GetMapping("/show-rooms")
	public String openShowRoomsPage(Model model) {
		// list of room to show all rooms
		List<Room> rooms = roomServices.allRooms();
		// uploaded the list, visible on view page through model
		model.addAttribute("rooms", rooms);
		return "customer/show-rooms";
	}
}
