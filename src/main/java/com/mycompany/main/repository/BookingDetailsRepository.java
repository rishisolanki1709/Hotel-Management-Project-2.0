package com.mycompany.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.main.entities.BookingDetails;

public interface BookingDetailsRepository extends JpaRepository<BookingDetails, Integer> {

}
