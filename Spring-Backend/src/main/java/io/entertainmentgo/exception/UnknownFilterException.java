package io.entertainmentgo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST)
public class UnknownFilterException extends Exception {

	private static final long serialVersionUID = 1L;
	public UnknownFilterException(String msg) {
		super(msg);
	}



}
