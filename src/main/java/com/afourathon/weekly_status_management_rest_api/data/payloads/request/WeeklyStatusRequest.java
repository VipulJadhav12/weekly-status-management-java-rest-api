package com.afourathon.weekly_status_management_rest_api.data.payloads.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeeklyStatusRequest {
	
	@NotBlank(message = "Weekly Status cannot be null.")
	private String status;
	
	private String highlight;
	
	private String risk;
	
	@NotBlank(message = "Weekly End Date cannot be null.")
	private String weeklyEndDate;
	
}
