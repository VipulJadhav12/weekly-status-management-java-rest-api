package com.afourathon.weekly_status_management_rest_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class WeeklyStatusManagementRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeeklyStatusManagementRestApiApplication.class, args);
	}

}
