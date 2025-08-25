package com.mycompany.main.entities;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table
@Entity
public class BookingDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long booking_Id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name ="customer_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="room_id")
	private Room room;

	//	@Column
//	private int customer_Id;
//	@Column
//	private int room_Id;
	@Column
	private String bookingMode; // Online or Offline(through Employee or by visiting Hotel)
	@Column
	private LocalDate checkInDate;
	@Column
	private LocalDate checkOutDate;
	@Column
	private LocalDate bookingDate;
	@Column
	private int totalAmount;
	@Column
	private int paidAmount;
	@Column
	private int pendingAmount;

	public long getBooking_Id() {
		return booking_Id;
	}

	public void setBooking_Id(long booking_Id) {
		this.booking_Id = booking_Id;
	}

	public String getBookingMode() {
		return bookingMode;
	}

	public void setBookingMode(String bookingMode) {
		this.bookingMode = bookingMode;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(int paidAmount) {
		this.paidAmount = paidAmount;
	}

	public int getPendingAmount() {
		return pendingAmount;
	}

	public void setPendingAmount(int pendingAmount) {
		this.pendingAmount = pendingAmount;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
}
