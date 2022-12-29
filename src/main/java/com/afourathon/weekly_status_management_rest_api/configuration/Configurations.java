package com.afourathon.weekly_status_management_rest_api.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Configurations {
	
	public static final String INVALID_PROJECT_ID = "Invalid Project ID. Project doesn't exists with ID: %d.";
		
	public static final String INVALID_WEEKLY_STATUS_ID = "Invalid Weekly-Status ID. Weekly-Status doesn't exists with ID: %d";
	public static final String WEEKLY_STATUS_ADD_ON_SUCCESS = "New Weekly-Status {ID: %d} has been added successfully!";
	public static final String WEEKLY_STATUS_ADD_ON_FAILED = "An error occured while adding new Weekly-Status into database.";
	public static final String WEEKLY_STATUS_UPDATE_ON_SUCCESS = "New Weekly-Status {ID: %d} has been updated successfully!";
	public static final String WEEKLY_STATUS_UPDATE_ON_FAILED = "An error occured while updating Weekly-Status with ID: %d.";
	public static final String WEEKLY_STATUS_ALREADY_EXIST = "Weekly-Status already exists with ID: %d for the Weekly_End_Date: %s under Project with ID: %d";
	public static final String DELETE_WEEKLY_STATUS_ON_SUCCESS = "Weekly-Status {ID: %d} has been deleted successfully!";
	public static final String DELETE_WEEKLY_STATUS_ON_FAILED = "An error occured while deleting Weekly-Status with ID: %d.";
	public static final String DELETE_ALL_WEEKLY_STATUSES_BY_PROJECT_ON_SUCCESS = "All Weekly-Statuses has been deleted successfully from the Project with ID: %d";
	public static final String DELETE_ALL_WEEKLY_STATUSES_BY_PROJECT_ON_FAILED = "An error occured while deleting all Weekly-Statuses from the Project with ID: %d";
	public static final String DELETE_ALL_WEEKLY_STATUSES_ON_SUCCESS = "All Weekly-Statuses has been deleted successfully!";
	public static final String DELETE_ALL_WEEKLY_STATUSES_ON_FAILED = "An error occured while deleting all Weekly-Statuses";

}
