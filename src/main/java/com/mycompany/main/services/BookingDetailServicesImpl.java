package com.mycompany.main.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.main.entities.BookingDetails;
import com.mycompany.main.repository.BookingDetailsRepository;

@Service
public class BookingDetailServicesImpl implements BookingDetailServices {

	@Autowired
	BookingDetailsRepository bookingDetailsRepository;
	
	@Override
	public long bookDetails(BookingDetails bookingDetails) {
		try {
			BookingDetails bookingDetails2 =  bookingDetailsRepository.save(bookingDetails);
			return bookingDetails2.getBooking_Id() ;
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public BookingDetails getBookingDetails(int id) {
		try {
			Optional<BookingDetails> bookOptional =  bookingDetailsRepository.findById(id);
			if(bookOptional.isPresent())
				return bookOptional.get();
			else
				return null;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String deleteBookingDetails(int id) {
		try {
			bookingDetailsRepository.deleteById(id);
			return "success";
		}catch(Exception e) {
			e.printStackTrace();
			return "failure";
		}
	}

	@Override
	public List<BookingDetails> allBookingDetails() {
		return bookingDetailsRepository.findAll();
	}

	
}
