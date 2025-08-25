package com.mycompany.main.services;

import java.util.List;

import com.mycompany.main.entities.BookingDetails;

public interface BookingDetailServices {
	long bookDetails(BookingDetails bookingDetails);
	BookingDetails getBookingDetails(int id);
	String deleteBookingDetails(int id);
	List<BookingDetails> allBookingDetails();
}
