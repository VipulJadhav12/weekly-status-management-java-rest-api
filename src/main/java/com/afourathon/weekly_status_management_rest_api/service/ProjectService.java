package com.afourathon.weekly_status_management_rest_api.service;

import java.util.List;
import java.util.Optional;

import com.afourathon.weekly_status_management_rest_api.data.entity.Project;
import com.afourathon.weekly_status_management_rest_api.data.entity.WeeklyStatus;

public interface ProjectService {
	
	public boolean checkIfProjectExistsById(Long projectId);
	
	public Optional<Project> findProjectById(Long projectId);
	
	List<Project> findAllrojects();

	public Project assignWeeklyStatusToProject(Long projectId, WeeklyStatus weeklyStatus);

	public WeeklyStatus assignUpdatedWeeklyStatusToProject(Long projectId, Long statusId, WeeklyStatus weeklyStatus);

}
