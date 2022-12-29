package com.afourathon.weekly_status_management_rest_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afourathon.weekly_status_management_rest_api.configuration.Configurations;
import com.afourathon.weekly_status_management_rest_api.data.entity.Project;
import com.afourathon.weekly_status_management_rest_api.exception.ProjectNotFoundException;
import com.afourathon.weekly_status_management_rest_api.service.ProjectService;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	Configurations configurations;
	
	@GetMapping(value = {"", "/"})
	public ResponseEntity<String> defaultHealthCheck() {
		return new ResponseEntity<String>("Default HTTP Status: OK", HttpStatus.OK);
	}
	
	@GetMapping("/getBy=ID/project/{projectId}")
	public ResponseEntity<Project> getProject(@PathVariable Long projectId) throws ProjectNotFoundException {
		if(!projectService.checkIfProjectExistsById(projectId)) {
			throw new ProjectNotFoundException(String.format(Configurations.INVALID_PROJECT_ID, projectId));
		}
		
		Optional<Project> objProject = projectService.findProjectById(projectId);
		
		if(!objProject.isPresent())
			throw new ProjectNotFoundException(String.format(Configurations.INVALID_PROJECT_ID, projectId));
		
		return ResponseEntity.ok(objProject.get());
	}
	
	@GetMapping("/getAllBy=NONE")
	public ResponseEntity<List<Project>> getAllProjectsByName() {
		List<Project> projects = projectService.findAllrojects();
		
		return ResponseEntity.ok(projects);
	}

}
