package com.mycompany.main.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.mycompany.main.entities.BookingDetails;
import com.mycompany.main.entities.Customer;
import com.mycompany.main.entities.Room;
import com.mycompany.main.services.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/NewRegistrationContoller")
public class NewRegistrationContoller {

	@Autowired
	RoomServices roomServices;

	@Autowired
	CustomerServices customerServices;

	@Autowired
	BookingDetailServices bookingDetailServices;

	@Autowired
	EmailServices emailServices;

	@PostMapping("/Register-New-Customer")
	public String openOpenRoomBookingPage(@ModelAttribute("newCustomer") Customer customer, Model model,
			HttpSession session) {
		session.setAttribute("customerName", customer.getName());
		session.setAttribute("customerEmail", customer.getEmail());
		session.setAttribute("customerAdhar", customer.getAdhar());
		session.setAttribute("customerPhone", customer.getPhone());
		session.setAttribute("customerGender", customer.getGender());
		session.setAttribute("customerAddress", customer.getAddress());
		List<Room> rooms = roomServices.allRooms();
		model.addAttribute("rooms", rooms);
		return "book-room";
	}

	@PostMapping("/room-booking-page/{id}")
	public String openRoomBookingConfirmPage(@PathVariable("id") int id, HttpSession session) {
		Room room = roomServices.findById(id).get();
		session.setAttribute("room", room);
		return "show-details";
	}

	@PostMapping("/Confirm_New_Registration")
	public String bookNewRegister(HttpSession session, @RequestParam("checkInDate") LocalDate checkInDate,
			@RequestParam("checkOutDate") LocalDate checkOutDate, @RequestParam("bookingMode") String bookingMode,
			@RequestParam("paidAmount") int paidAmount, @RequestParam("pendingAmount") int pendingAmount,
			@RequestParam("total") int total, RedirectAttributes redirectAttributes, Model model) {

		Customer customer = new Customer();
		String customerName = session.getAttribute("customerName").toString();
		String customerEmail = session.getAttribute("customerEmail").toString();
		customer.setName(customerName);
		customer.setEmail(customerEmail);
		customer.setPhone(session.getAttribute("customerPhone").toString());
		customer.setAdhar(session.getAttribute("customerAdhar").toString());
		customer.setGender(session.getAttribute("customerGender").toString());
		customer.setAddress(session.getAttribute("customerAddress").toString());
		String response = customerServices.addNewCustomer(customer);
		if (response.equals("success")) {
			Room room = (Room) session.getAttribute("room");
			int roomId = room.getRoom_Id();
			BookingDetails bookingDetails = new BookingDetails();
			bookingDetails.setBookingDate(LocalDate.now());
			bookingDetails.setBookingMode(bookingMode);
			bookingDetails.setCheckInDate(checkInDate);
			bookingDetails.setCheckOutDate(checkOutDate);
			bookingDetails.setPaidAmount(paidAmount);
			bookingDetails.setPendingAmount(pendingAmount);
			bookingDetails.setTotalAmount(total);
			bookingDetails.setCustomer(customer);
			bookingDetails.setRoom(room);
			long bookingId = 0;
			bookingId = bookingDetailServices.bookDetails(bookingDetails);

			room.setAvailability("Booked");
			String emailMessageBody = "Dear " + customerName + ",\n" + "Email " + customerEmail + ",\n\n"
					+ "Thank you for choosing Velvet Vista Hotel !\n\n" + "Here are your booking details:\n"
					+ "----------------------------------------\n" + "Booking ID     : " + bookingId + "\n"
					+ "Room Number    : " + roomId +  "\n"
					+ "Booking Date   : " + LocalDate.now() + "\n" + "Check-In Date  : " + checkInDate + "\n"
					+ "Check-Out Date : " + checkOutDate + "\n\n" + "Total Amount   : ₹" + total + "\n"
					+ "Paid Amount    : ₹" + paidAmount + "\n" + "Pending Amount : ₹" + pendingAmount + "\n"
					+ "----------------------------------------\n\n"
					+ "We look forward to welcoming you to our hotel and ensuring you have a comfortable stay.\n\n"
					+ "Warm regards,\n" + "Velvet Vista Hotel Team\n"
					+ "Contact: +91-123567890 | Email: support@velvetvista.com";
			String roomUpdateResponse = roomServices.updateRoomDetail(room);
			// mail sending process
			String mailSubject = "Room Booking Confirmation Mail";
			String mailResponse = emailServices.sendEmail(customerEmail, mailSubject, emailMessageBody);
			if (bookingId != 0 && roomUpdateResponse.equals("Room Updated success") && mailResponse.equals("success")) {
				// Everything is Current
				session.removeAttribute("customerName");
				session.removeAttribute("customerEmail");
				session.removeAttribute("customerAdhar");
				session.removeAttribute("customerPhone");
				session.removeAttribute("customerGender");
				session.removeAttribute("customerAddress");
				session.removeAttribute("room");
				redirectAttributes.addFlashAttribute("msg",
						"Your Registration was Successfully completed. You'll be receive Email shortly, containing detailed Info about Transastion... Thank You!");

				if (session.getAttribute("employeeName") != null) {
					return "redirect:/employee/home";
				} else {
					return "redirect:/customer/home";
				}
			} else {
				model.addAttribute("msg", "somthing went wrong");
				if (session.getAttribute("employeeName") != null) {
					return "employee/home";
				} else {
					return "customer/home";
				}
			}
		} else {
			model.addAttribute("msg", "somthing went wrong");
			if (session.getAttribute("employeeName") != null) {
				return "employee/home";
			} else {
				return "customer/home";
			}
		}
	}
}
