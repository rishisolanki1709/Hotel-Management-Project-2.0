package com.mycompany.main.services;

import java.util.List;
import java.util.Optional;

import com.mycompany.main.entities.Room;

public interface RoomServices {

	List<Room> allRooms();
	String addNewRoom(Room room);
	Optional<Room> findById(int id);
	void removeRoom(int id);
	String updateRoomDetail(Room updatedRoom);
}
