package com.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.security.domain.Guest;
import com.security.exceptions.GuestNotFoundException;
import com.security.model.GuestModel;
import com.security.repository.GuestRepository;

@Service
public class GuestService {

	private final GuestRepository repository;

	public GuestService(GuestRepository repository) {
		super();
		this.repository = repository;
	}

	public List<Guest> getAllGuests() {
		return new ArrayList<>(repository.findAll());
	}

	public Guest addGuest(@RequestBody GuestModel model) {
		return repository.save(model.translateToDomain());
	}

	public Guest getGuest(@PathVariable Long id) {
		Optional<Guest> guest = repository.findById(id);
		if (guest.isPresent()) {
			return guest.get();
		}
		throw new GuestNotFoundException("Guest not found with id: " + id);
	}

	public Guest updateGuest(@PathVariable Long id, @RequestBody GuestModel model) {
		Optional<Guest> existing = repository.findById(id);
		if (!existing.isPresent()) {
			throw new GuestNotFoundException("Guest not found with id: " + id);
		}
		Guest guest = model.translateToDomain();
		guest.setId(id);
		return repository.save(guest);
	}

	public void deleteGuest(@PathVariable Long id) {
		Optional<Guest> existing = repository.findById(id);
		if (!existing.isPresent()) {
			throw new GuestNotFoundException("Guest not found with id: " + id);
		}
		repository.deleteById(id);
	}
}
