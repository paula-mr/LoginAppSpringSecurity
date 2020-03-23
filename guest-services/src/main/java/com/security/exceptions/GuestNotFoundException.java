package com.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GuestNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3514018767885152025L;

	public GuestNotFoundException(String s) {
		super(s);
	}
}
