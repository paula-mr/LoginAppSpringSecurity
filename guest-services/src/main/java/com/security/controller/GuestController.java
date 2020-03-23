package com.security.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.security.domain.Guest;
import com.security.exceptions.GuestNotFoundException;
import com.security.model.GuestModel;
import com.security.service.GuestService;

@RestController
@RequestMapping("/guests")
public class GuestController {

	private final GuestService service;

	public GuestController(GuestService service) {
		super();
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<Object> getAllGuests() {
		try {
			return ResponseEntity.ok(service.getAllGuests());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping
	public ResponseEntity<Guest> addGuest(@RequestBody GuestModel model) {
		Guest guest;
		URI location;

		try {
			guest = service.addGuest(model);
			location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(guest.getId())
					.toUri();
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.created(location).body(guest);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Guest> getGuest(@PathVariable Long id) {
		Guest guest;

		try {
			guest = service.getGuest(id);
		} catch (GuestNotFoundException ex) {
			return ResponseEntity.notFound().build();
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		return ResponseEntity.ok(guest);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Guest> updateGuest(@PathVariable Long id, @RequestBody GuestModel model) {
		Guest guest;

		try {
			guest = service.updateGuest(id, model);
		} catch (GuestNotFoundException ex) {
			return ResponseEntity.notFound().build();
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		return ResponseEntity.ok(guest);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.RESET_CONTENT)
	public ResponseEntity<Object> deleteGuest(@PathVariable Long id) {
		try {
			service.deleteGuest(id);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		return ResponseEntity.noContent().build();
	}
}
