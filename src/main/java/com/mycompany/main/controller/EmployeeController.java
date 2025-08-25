package com.mycompany.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycompany.main.entities.BookingDetails;
import com.mycompany.main.entities.Customer;
import com.mycompany.main.entities.Employee;
import com.mycompany.main.entities.Room;
import com.mycompany.main.services.BookingDetailServices;
import com.mycompany.main.services.CustomerServices;
import com.mycompany.main.services.EmailServices;
import com.mycompany.main.services.EmployeeServices;
import com.mycompany.main.services.RoomServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	EmployeeServices employeeServices;

	@Autowired
	RoomServices roomServices;

	@Autowired
	CustomerServices customerServices;

	@Autowired
	BookingDetailServices bookingDetailServices;

	@Autowired
	EmailServices emailServices;

	@PostMapping("/home")
	public String employeeHome(@RequestParam("employee-email") String email,
			@RequestParam("employee-password") String password, Model model, HttpSession session,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {

		if (session == null) {
			session = request.getSession(true);
		}

		String response = employeeServices.findByEmail(email.trim(), password.trim(), session);

		if (response.equals("Email Not Found") || response.equals("Invalid Password")) {
			redirectAttributes.addFlashAttribute("msg", response);
			return "redirect:/";
		} else if (response.equals("success")) {

			// list of employee to show all employees
			List<Employee> employees = employeeServices.allEmployees();
			// uploaded the list, visible on view page through model
			model.addAttribute("employees", employees);

			// list of room to show all rooms
			List<Room> rooms = roomServices.allRooms();
			// uploaded the list, visible on view page through model
			model.addAttribute("rooms", rooms);

			// list of room to show all rooms
			List<Customer> customers = customerServices.allCustomers();
			// uploaded the list, visible on view page through model
			model.addAttribute("customers", customers);

			// list of employee to show all employees
			List<BookingDetails> bookingDetails = bookingDetailServices.allBookingDetails();
			// uploaded the list, visible on view page through model
			model.addAttribute("bookingDetails", bookingDetails);

			// to get the new customer details the new Customer Object is passed
			model.addAttribute("newCustomer", new Customer());

			return "employee/home";
		} else {
			model.addAttribute("msg", "somthing went wrong");
			return "index";
		}
	}

	@GetMapping("/home")
	public String employeeHome(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		if (session.getAttribute("employeeName") == null) {
			redirectAttributes.addFlashAttribute("msg", "Login your account first");
			return "redirect:/index";
		}
		// Always fetch fresh list from DB
		List<Employee> employees = employeeServices.allEmployees();
		List<Room> rooms = roomServices.allRooms();
		List<Customer> customers = customerServices.allCustomers();
		model.addAttribute("rooms", rooms);
		model.addAttribute("employees", employees);
		model.addAttribute("customers", customers);
		List<BookingDetails> bookingDetails = bookingDetailServices.allBookingDetails();
		// uploaded the list, visible on view page through model
		model.addAttribute("bookingDetails", bookingDetails);
		return "employee/home";
	}

	@PostMapping("/update-customer-page/{id}")
	public String openCustomerUpdatePage(@PathVariable("id") int id, Model model, HttpSession session) {
		if (session.getAttribute("employeeName") == null) {
			model.addAttribute("msg", "Login your account first");
			return "index";
		}
		Customer customer = customerServices.getCustomerById(id);
		model.addAttribute(customer);
		return "employee/update-customer";
	}

	@PostMapping("/Update_Customer_Detail")
	public String updateCustomerDetails(@RequestParam("customerId") int id, @RequestParam("name") String name,
			@RequestParam("email") String email, @RequestParam("phone") String phone,
			@RequestParam("gender") String gender, @RequestParam("adhar") String adhar,
			@RequestParam("address") String address, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		if (session.getAttribute("employeeName") == null) {
			redirectAttributes.addFlashAttribute("msg", "Login your account first");
			return "redirect:/index";
		}
		try {

			Customer customer = customerServices.getCustomerById(id);
			customer.setName(name);
			customer.setEmail(email);
			customer.setPhone(phone);
			customer.setAdhar(adhar);
			customer.setGender(gender);
			customer.setAddress(address);
			String response = customerServices.updateCustomerDetails(customer);
			if (response.equals("Customer Updated Successfully")) {
				redirectAttributes.addFlashAttribute("msg", response);
			} else {
				redirectAttributes.addFlashAttribute("msg", response);
			}
			return "redirect:/employee/home";
		} catch (Exception e) {
			e.printStackTrace();
			return "employee/home";
		}
	}

	@PostMapping("/Check_Out")
	public String openCheckOutPage(@RequestParam("booking-id") int id, Model model,
			RedirectAttributes redirectAttributes, HttpSession session) {
		if (session.getAttribute("employeeName") == null) {
			redirectAttributes.addFlashAttribute("msg", "Login your account first");
			return "redirect:/index";
		}

		BookingDetails bookingDetails = bookingDetailServices.getBookingDetails(id);
		if (bookingDetails != null) {
			Room room = bookingDetails.getRoom();
			Customer customer = bookingDetails.getCustomer();
			model.addAttribute("room", room);
			model.addAttribute("customer", customer);
			model.addAttribute("bookingDetails", bookingDetails);
			return "employee/check-out";
		} else {
			redirectAttributes.addFlashAttribute("msg", "Booking Id Not Found...");
			return "redirect:/employee/home";
		}
	}

	@PostMapping("/Check_Out_Confirm")
	public String checkOutCustomer(@RequestParam("bookingId") int bookingId, RedirectAttributes redirectAttributes,
			HttpSession session) {

		if (session.getAttribute("employeeName") == null) {
			redirectAttributes.addFlashAttribute("msg", "Login your account first");
			return "redirect:/index";
		}

		try {
			BookingDetails bookingDetails = bookingDetailServices.getBookingDetails(bookingId);
			Room room = bookingDetails.getRoom();
			room.setAvailability("Available");
			roomServices.updateRoomDetail(room);
			String response = bookingDetailServices.deleteBookingDetails(bookingId);

			String customerEmail = bookingDetails.getCustomer().getEmail();
			String customerName = bookingDetails.getCustomer().getName();
			String mailSubject = "Check-Out Confirmation Mail";
			String mailBody = "Dear " + customerName + ",\n\n"
					+ "Thank you for staying with us at Velvet Vista Hotel.\n"
					+ "We hope you had a pleasant experience.\n\n" + "Here are your checkout details:\n"
					+ "Booking ID: " + bookingId + "\n" + "Check-In: " + bookingDetails.getCheckInDate() + "\n"
					+ "Check-Out: " + bookingDetails.getCheckOutDate() + "\n\n"
					+ "We truly appreciate your visit and look forward to welcoming you again.\n\n" + "Best regards,\n"
					+ "Team Velvet Vista\n";

			String mailResponse = emailServices.sendEmail(customerEmail, mailSubject, mailBody); 
			if (response.equals("success") && mailResponse.equals("success")
) {

				redirectAttributes.addFlashAttribute("msg",
						"Check-Out Successfull.Customer will receive Email Shortly...");
				return "redirect:/employee/home";
			} else {
				redirectAttributes.addFlashAttribute("msg", "somthing went wrong");
				return "redirect:/employee/home";
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", "somthing went wrong");
			return "redirect:/employee/home";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		if (session.getAttribute("employeeName") != null) {
			session.invalidate();
		}
		return "redirect:/";
	}
}
