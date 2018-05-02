package io.entertainmentgo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST)
public class UnknownSortException extends Exception {

	private static final long serialVersionUID = 1L;

	public UnknownSortException(String msg) {
		super(msg);
	}

}
