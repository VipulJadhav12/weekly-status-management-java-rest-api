package com.afourathon.weekly_status_management_rest_api.exception;

public class WeeklyStatusAlreadyExistException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public WeeklyStatusAlreadyExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public WeeklyStatusAlreadyExistException(String message) {
		super(message);
	}

	public WeeklyStatusAlreadyExistException(Throwable cause) {
		super(cause);
	}

}
