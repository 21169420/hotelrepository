package net.guides.springboot2.springboot2jpacrudexample.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.guides.springboot2.springboot2jpacrudexample.exception.ResourceNotFoundException;
import net.guides.springboot2.springboot2jpacrudexample.model.Guest;
import net.guides.springboot2.springboot2jpacrudexample.repository.GuestRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class GuestController {
	@Autowired
	private GuestRepository guestRepository;

	@GetMapping("/guest")
	public List<Guest> getAllguest() {
		return guestRepository.findAll();
	}

	@GetMapping("/guest/{id}")
	public ResponseEntity<Guest> getGuestById(@PathVariable(value = "id") Long guestId)
			throws ResourceNotFoundException {
		Guest guest = guestRepository.findById(guestId)
				.orElseThrow(() -> new ResourceNotFoundException("Guest not found for this id :: " + guestId));
		return ResponseEntity.ok().body(guest);
	}

	@PostMapping("/guest")
	public Guest createGuest(@RequestBody Guest guest) {
		return guestRepository.save(guest);
	}

	@PutMapping("/guest/{id}")
	public ResponseEntity<Guest> updateGuest(@PathVariable(value = "id") Long guestId,
			@RequestBody Guest guestDetails) throws ResourceNotFoundException {
		Guest guest = guestRepository.findById(guestId)
				.orElseThrow(() -> new ResourceNotFoundException("Guest not found for this id :: " + guestId));

		guest.setEmailId(guestDetails.getEmailId());
		guest.setLastName(guestDetails.getLastName());
		guest.setFirstName(guestDetails.getFirstName());
		guest.setRoomNo(guestDetails.getRoomNo());
		guest.setStartDate(guestDetails.getStartDate());
		guest.setEndDate(guestDetails.getEndDate());
		final Guest updatedGuest = guestRepository.save(guest);
		return ResponseEntity.ok(updatedGuest);
	}

	@DeleteMapping("/guest/{id}")
	public Map<String, Boolean> deleteGuest(@PathVariable(value = "id") Long guestId)
			throws ResourceNotFoundException {
		Guest guest = guestRepository.findById(guestId)
				.orElseThrow(() -> new ResourceNotFoundException("Guest not found for this id :: " + guestId));

		guestRepository.delete(guest);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
