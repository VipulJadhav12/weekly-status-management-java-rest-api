package com.afourathon.weekly_status_management_rest_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.afourathon.weekly_status_management_rest_api.data.entity.Project;
import com.afourathon.weekly_status_management_rest_api.data.entity.WeeklyStatus;
import com.afourathon.weekly_status_management_rest_api.data.payloads.repository.ProjectRepository;

@Service
public class JpaProjectService implements ProjectService {

	@Autowired
	ProjectRepository projectRepository;

	@Override
	public boolean checkIfProjectExistsById(Long projectId) {
		return projectRepository.existsById(projectId);
	}

	@Override
	public Optional<Project> findProjectById(Long projectId) {
		return projectRepository.findById(projectId);
	}

	@Override
	public List<Project> findAllrojects() {
		return projectRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}

	@Override
	public Project assignWeeklyStatusToProject(Long projectId, WeeklyStatus weeklyStatus) {
		// Find the Project by ID
		Optional<Project> objProject = projectRepository.findById(projectId);

		// Check if the Project entity is present
		if(objProject.isPresent()) {
			// Get the actual Project entity
			Project project = objProject.get();
			// Add the Weekly-Status into the Project's Weekly-Statuses list
			project.getWeeklyStatuses().add(weeklyStatus);

			try {
				// Save the updated Project entity into database
				return projectRepository.save(project);
			}
			catch(IllegalArgumentException ex) {
				return null;
			}
			catch(OptimisticLockingFailureException ex) {
				return null;
			}
		}

		return null;

	}

	@Override
	public WeeklyStatus assignUpdatedWeeklyStatusToProject(Long projectId, Long statusId, WeeklyStatus weeklyStatus) {
		// Find the Project by ID
		Optional<Project> objProject = projectRepository.findById(projectId);

		// Check if the Project entity is present
		if(objProject.isPresent()) {
			// Get the actual Project entity
			Project project = objProject.get();

			// Get the list of all attached Weekly-Statuses with the given Project ID
			List<WeeklyStatus> weeklyStatuses = project.getWeeklyStatuses();

			// Filtering and fetching the already existing/assigned Weekly-Status from the above Weekly-Statuses list.
			// Once the existing Weekly-Status is found, will remove it.
			WeeklyStatus existingWeeklyStatusToBeRemoved = weeklyStatuses.stream()
					.filter(existingWeeklyStatus -> existingWeeklyStatus.getId().equals(statusId))
					.findFirst()
					.orElse(null);

			// Removing the existing (old) Weekly-Status from the given Project ID
			if(null != existingWeeklyStatusToBeRemoved)
				weeklyStatuses.remove(existingWeeklyStatusToBeRemoved);

			// Adding the updated (new) Weekly-Status to the given Project ID
			weeklyStatuses.add(weeklyStatus);
			project.setWeeklyStatuses(weeklyStatuses);

			try {
				// Save the updated Project entity into database
				Project projectWithUpdatedWeeklyStatus =  projectRepository.save(project);
				
				if(null != project) {
					// Retrieve the Weekly-Statuses assigned to the above Project entity
					List<WeeklyStatus> updatedWeeklyStatusList = projectWithUpdatedWeeklyStatus.getWeeklyStatuses();
					
					// Filter and return the newly updated Weekly-Status.
					// If not found the return null;
					return updatedWeeklyStatusList.stream()
							  .filter(existingWeeklyStatus -> existingWeeklyStatus.getId().equals(statusId))
							  .findAny()
							  .orElse(null);
				}
			}
			catch(IllegalArgumentException ex) {
				return null;
			}
			catch(OptimisticLockingFailureException ex) {
				return null;
			}
		}

		return null;
	}

}
