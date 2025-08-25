package com.mycompany.main.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int room_Id;
	@Column
	private int price;
	@Column
	private String bedSize;
	@Column
	private String availability;
	public int getRoom_Id() {
		return room_Id;
	}
	public void setRoom_Id(int room_Id) {
		this.room_Id = room_Id;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getBedSize() {
		return bedSize;
	}
	public void setBedSize(String bedSize) {
		this.bedSize = bedSize;
	}
	public String getAvailability() {
		return availability;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}
	
	
}
