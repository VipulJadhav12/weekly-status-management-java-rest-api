package com.afourathon.weekly_status_management_rest_api.exception;

public class WeeklyStatusNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public WeeklyStatusNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public WeeklyStatusNotFoundException(String message) {
		super(message);
	}

	public WeeklyStatusNotFoundException(Throwable cause) {
		super(cause);
	}

}
