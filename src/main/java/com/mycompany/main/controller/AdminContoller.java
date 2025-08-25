package com.mycompany.main.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycompany.main.entities.BookingDetails;
import com.mycompany.main.entities.Employee;
import com.mycompany.main.entities.Room;
import com.mycompany.main.services.AdminServices;
import com.mycompany.main.services.BookingDetailServices;
import com.mycompany.main.services.EmployeeServices;
import com.mycompany.main.services.RoomServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminContoller {

	@Autowired
	AdminServices adminServices;

	@Autowired
	EmployeeServices employeeServices;

	@Autowired
	RoomServices roomServices;

	@Autowired
	BookingDetailServices bookingDetailServices;

	@PostMapping("/home")
	public String adminHome(@RequestParam("admin-email") String email, @RequestParam("admin-password") String password,
			Model model, HttpSession session, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (session == null) {
			session = request.getSession(true);
		}
		String response = adminServices.findByEmail(email.trim(), password.trim(), session);

		if (response.equals("Email Not Found") || response.equals("Invalid Password")) {
			redirectAttributes.addFlashAttribute("msg", response);
			return "redirect:/";
		} else if (response.equals("success")) {
			// main work here

			// list of employee to show all employees
			List<Employee> employees = employeeServices.allEmployees();
			// uploaded the list, visible on view page through model
			model.addAttribute("employees", employees);

			// list of room to show all rooms
			List<Room> rooms = roomServices.allRooms();
			// uploaded the list, visible on view page through model
			model.addAttribute("rooms", rooms);

			// list of employee to show all employees
			List<BookingDetails> bookingDetails = bookingDetailServices.allBookingDetails();
			// uploaded the list, visible on view page through model
			model.addAttribute("bookingDetails", bookingDetails);

			// to get the new employee details the new Employee Object is passed
			model.addAttribute("newEmployee", new Employee());

			// to get the new room details the new Room Object is passed
			model.addAttribute("newRoom", new Room());

			return "/admin/home";
		} else {
			model.addAttribute("msg", "somthing went wrong");
			return "index";
		}
	}

	@GetMapping("/home")
	public String adminHome(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		if (session.getAttribute("adminName") == null) {
			redirectAttributes.addFlashAttribute("msg", "Login your account first");
			return "redirect:/index";
		}
		// Always fetch fresh list from DB
		List<Employee> employees = employeeServices.allEmployees();
		List<Room> rooms = roomServices.allRooms();
		model.addAttribute("rooms", rooms);
		model.addAttribute("employees", employees);
		// uploaded the list, visible on view page through model
		List<BookingDetails> bookingDetails = bookingDetailServices.allBookingDetails();
		// uploaded the list, visible on view page through model
		model.addAttribute("bookingDetails", bookingDetails);

		return "admin/home";
	}

	@PostMapping("/Register-New-Employee")
	public String registerNewEmployee(@ModelAttribute("newEmployee") Employee newEmployee, Model model,
			HttpSession session, RedirectAttributes redirectAttributes) {
		if (session.getAttribute("adminName") == null) {
			redirectAttributes.addFlashAttribute("msg", "Login your account first");
			return "redirect:/index";
		}
		String response = employeeServices.addNewEmployee(newEmployee);
		if (response.equals("success")) {
			redirectAttributes.addFlashAttribute("msg", "Employee Data Stored");
		} else {
			redirectAttributes.addFlashAttribute("msg", "Something went wrong");
		}
		return "redirect:/admin/home";
	}

	@PostMapping("/Register-New-Room")
	public String registerNewRoom(@ModelAttribute("newRoom") Room room, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		if (session.getAttribute("adminName") == null) {
			redirectAttributes.addFlashAttribute("msg", "Login your account first");
			return "redirect:/index";
		}
		String response = roomServices.addNewRoom(room);
		if (response.equals("success")) {
			redirectAttributes.addFlashAttribute("msg", "Room Data Stored");
		} else {
			redirectAttributes.addFlashAttribute("msg", "Something went wrong");
		}
		return "redirect:/admin/home";
	}

	@PostMapping("/employee-update-page/{id}")
	public String openEmployeeUpdatePage(@PathVariable("id") int id, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		if (session.getAttribute("adminName") == null) {
			redirectAttributes.addFlashAttribute("msg", "Login your account first");
			return "index";
		}
		Employee employee = employeeServices.findById(id).get();
		model.addAttribute(employee);
		return "admin/update";
	}

	@PostMapping("/room-update-page/{id}")
	public String openRoomUpdatePage(@PathVariable("id") int id, Model model, HttpSession session) {
		if (session.getAttribute("adminName") == null) {
			model.addAttribute("msg", "Login your account first");
			return "index";
		}
		Room room = roomServices.findById(id).get();
		model.addAttribute(room);
		return "admin/update";
	}

	@PostMapping("/Update_Employee_Detail")
	public String updateEmployeeDetails(@RequestParam("employeeId") int id, @RequestParam("name") String name,
			@RequestParam("email") String email, @RequestParam("phone") String phone,
			@RequestParam("gender") String gender, @RequestParam("jobRole") String jobRole,
			@RequestParam("salary") String salary, @RequestParam("password") String password, Model model,
			HttpSession session, RedirectAttributes redirectAttributes) {
		if (session.getAttribute("adminName") == null) {
			redirectAttributes.addFlashAttribute("msg", "Login your account first");
			return "redirect:/index";
		}
		try {

			Optional<Employee> emp = employeeServices.findById(id);
			if (emp.isPresent()) {
				Employee employee = emp.get();
				employee.setName(name);
				employee.setEmail(email);
				employee.setGender(gender);
				employee.setJobRole(jobRole);
				employee.setPassword(password);
				employee.setSalary(salary);
				employee.setPhone(phone);
				String response = employeeServices.updateEmployeeDetail(employee);
				if (response.equals("Employee Updated Successfully")) {
					redirectAttributes.addFlashAttribute("msg", response);
				} else {
					redirectAttributes.addFlashAttribute("msg", response);
				}
			}
			return "redirect:/admin/home";
		} catch (Exception e) {
			e.printStackTrace();
			return "admin/home";
		}
	}

	@PostMapping("/Update_Room_Detail")
	public String updateRoomDetails(@RequestParam("room_Id") int id, @RequestParam("price") int price,
			@RequestParam("availability") String availability, @RequestParam("bedSize") String bedSize, Model model,
			HttpSession session, RedirectAttributes redirectAttributes) {
		if (session.getAttribute("adminName") == null) {
			redirectAttributes.addFlashAttribute("msg", "Login your account first");
			return "redirect:/index";
		}
		try {

			Optional<Room> room = roomServices.findById(id);
			if (room.isPresent()) {
				Room updatedRoom = room.get();
				updatedRoom.setPrice(price);
				updatedRoom.setAvailability(availability);
				updatedRoom.setBedSize(bedSize);
				String response = roomServices.updateRoomDetail(updatedRoom);
				if (response.equals("Room Updated success")) {
					redirectAttributes.addFlashAttribute("msg", response);
				} else {
					redirectAttributes.addFlashAttribute("msg", response);
				}
			}
			return "redirect:/admin/home";
		} catch (Exception e) {
			e.printStackTrace();
			return "admin/home";
		}
	}

	@PostMapping("/delete-employee/{id}")
	public String removeEmployee(@PathVariable("id") int id, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		if (session.getAttribute("adminName") == null) {
			redirectAttributes.addFlashAttribute("msg", "Login your account first");
			return "redirect:/index";
		}
		try {
			employeeServices.removeEmployee(id);
			redirectAttributes.addFlashAttribute("msg", "Employee Removed Successfully");
			return "redirect:/admin/home";
		} catch (Exception e) {
			e.printStackTrace();
			return "admin/home";
		}
	}

	@PostMapping("/delete-room/{id}")
	public String removeRoom(@PathVariable("id") int id, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		if (session.getAttribute("adminName") == null) {
			redirectAttributes.addFlashAttribute("msg", "Login your account first");
			return "redirect:/index";
		}
		try {
			roomServices.removeRoom(id);
			return "redirect:/admin/home";
		} catch (Exception e) {
			e.printStackTrace();
			return "admin/home";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		if (session.getAttribute("adminName") != null) {
			session.invalidate();
		}
		return "redirect:/";
	}
}
