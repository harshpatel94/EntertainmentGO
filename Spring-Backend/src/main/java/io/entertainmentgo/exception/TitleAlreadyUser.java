package io.entertainmentgo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.FOUND)
public class TitleAlreadyUser extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TitleAlreadyUser(String msg) {
		super(msg);
	}

}
