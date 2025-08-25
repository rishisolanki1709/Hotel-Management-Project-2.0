package com.mycompany.main.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.main.entities.Room;
import com.mycompany.main.repository.RoomRepository;

@Service
public class RoomServicesImpl implements RoomServices {

	@Autowired
	RoomRepository roomRepository;
	
	@Override
	public List<Room> allRooms() {
		return roomRepository.findAll();
	}

	@Override
	public String addNewRoom(Room room) {
		try {
			roomRepository.save(room);
			return "success";
		}catch(Exception e) {
			e.printStackTrace();
			return "failure";
		}
	}

	@Override
	public Optional<Room> findById(int id) {
		return roomRepository.findById(id);
	}

	@Override
	public void removeRoom(int id) {
		roomRepository.deleteById(id);
	}

	@Override
	public String updateRoomDetail(Room updatedRoom) {
		try {
			roomRepository.save(updatedRoom);
			return "Room Updated success";
		}catch(Exception e) {
			e.printStackTrace();
			return "Updation failure";
		}
	}

}
