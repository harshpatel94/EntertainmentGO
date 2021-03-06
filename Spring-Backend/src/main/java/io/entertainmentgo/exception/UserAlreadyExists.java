package io.entertainmentgo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.FOUND)
public class UserAlreadyExists extends Exception {

	private static final long serialVersionUID = 1L;

	public UserAlreadyExists(String string) {
		super(string);
	}

}
