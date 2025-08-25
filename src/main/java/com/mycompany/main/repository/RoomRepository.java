package com.mycompany.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.main.entities.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
	
}
