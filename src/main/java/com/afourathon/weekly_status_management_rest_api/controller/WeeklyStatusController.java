package com.afourathon.weekly_status_management_rest_api.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afourathon.weekly_status_management_rest_api.configuration.Configurations;
import com.afourathon.weekly_status_management_rest_api.data.entity.WeeklyStatus;
import com.afourathon.weekly_status_management_rest_api.data.payloads.request.WeeklyStatusRequest;
import com.afourathon.weekly_status_management_rest_api.data.payloads.response.ApiResponse;
import com.afourathon.weekly_status_management_rest_api.exception.ProjectNotFoundException;
import com.afourathon.weekly_status_management_rest_api.exception.WeeklyStatusAlreadyExistException;
import com.afourathon.weekly_status_management_rest_api.exception.WeeklyStatusNotFoundException;
import com.afourathon.weekly_status_management_rest_api.service.ProjectService;
import com.afourathon.weekly_status_management_rest_api.service.WeeklyStatusService;

@RestController
@RequestMapping("/api/v1/weekly_statuses")
public class WeeklyStatusController {
	
	@Autowired
	WeeklyStatusService weeklyStatusService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	Configurations configurations;
	
	@GetMapping(value = {"", "/"})
	public ResponseEntity<String> defaultHealthCheck() {
		return new ResponseEntity<>("Default HTTP Status: OK", HttpStatus.OK);
	}
	
	@GetMapping("/getBy=ID/weekly_status/{weeklyStatusId}")
	public ResponseEntity<WeeklyStatus> getWeeklyStatus(@PathVariable Long weeklyStatusId) throws WeeklyStatusNotFoundException {
		if(!weeklyStatusService.checkIfWeeklyStatusExistsById(weeklyStatusId)) {
			throw new WeeklyStatusNotFoundException(String.format(Configurations.INVALID_WEEKLY_STATUS_ID, weeklyStatusId));
		}
		
		Optional<WeeklyStatus> objWeeklyStatus = weeklyStatusService.findWeeklyStatusById(weeklyStatusId);
		
		if(!objWeeklyStatus.isPresent())
			throw new WeeklyStatusNotFoundException(String.format(Configurations.INVALID_WEEKLY_STATUS_ID, weeklyStatusId));
		
		return ResponseEntity.ok(objWeeklyStatus.get());
	}
	
	@GetMapping("/getBy=PROJECT_ID/project/{projectId}/weekly_status/{weeklyStatusId}")
	public ResponseEntity<WeeklyStatus> getWeeklyStatusByIdAndProjectId(@PathVariable Long projectId, @PathVariable Long weeklyStatusId) 
			throws WeeklyStatusNotFoundException, ProjectNotFoundException {
		if(!projectService.checkIfProjectExistsById(projectId)) {
			throw new ProjectNotFoundException(String.format(Configurations.INVALID_PROJECT_ID, projectId));
		}

		if(!weeklyStatusService.checkIfWeeklyStatusExistsById(weeklyStatusId)) {
			throw new WeeklyStatusNotFoundException(String.format(Configurations.INVALID_WEEKLY_STATUS_ID, weeklyStatusId));
		}
		
		Optional<WeeklyStatus> objWeeklyStatus = weeklyStatusService.findWeeklyStatusByIdAndByProjectId(weeklyStatusId, projectId);
		
		if(!objWeeklyStatus.isPresent())
			throw new WeeklyStatusNotFoundException(String.format(Configurations.INVALID_WEEKLY_STATUS_ID, weeklyStatusId));
		
		return ResponseEntity.ok(objWeeklyStatus.get());
	}
	
	@GetMapping("/getAllBy=PROJECT_ID/pagination=FALSE/project/{projectId}")
	public ResponseEntity<List<WeeklyStatus>> getAllWeeklyStatusesByProjectId(@PathVariable Long projectId) throws ProjectNotFoundException {
		if(!projectService.checkIfProjectExistsById(projectId)) {
			throw new ProjectNotFoundException(String.format(Configurations.INVALID_PROJECT_ID, projectId));
		}
		
		List<WeeklyStatus> weeklyStatuses = weeklyStatusService.findAllWeeklyStatusesByProjectId(projectId);
		
		return ResponseEntity.ok(weeklyStatuses);
	}
	
	@GetMapping("/getAllBy=PROJECT_ID/pagination=TRUE/offset/{offset}/pageSize/{pageSize}/project/{projectId}")
	public ResponseEntity<Page<WeeklyStatus>> getAllWeeklyStatusesByProjectId(@PathVariable int offset, @PathVariable int pageSize, @PathVariable Long projectId) 
			throws ProjectNotFoundException {
		if(!projectService.checkIfProjectExistsById(projectId)) {
			throw new ProjectNotFoundException(String.format(Configurations.INVALID_PROJECT_ID, projectId));
		}
		
		Page<WeeklyStatus> weeklyStatuses = weeklyStatusService.findAllWeeklyStatusesByProjectId(projectId, offset, pageSize);
		
		return ResponseEntity.ok(weeklyStatuses);
	}
	
	@GetMapping("/getAllBy=NONE")
	public ResponseEntity<List<WeeklyStatus>> getAllWeeklyStatuses() {
		List<WeeklyStatus> weeklyStatuses = weeklyStatusService.findAllWeeklyStatuses();
		
		return ResponseEntity.ok(weeklyStatuses);
	}
	
	@GetMapping("/getAllBy=NONE/pagination=TRUE/offset/{offset}/pageSize/{pageSize}")
	public ResponseEntity<Page<WeeklyStatus>> getAllWeeklyStatuses(@PathVariable int offset, @PathVariable int pageSize) {
		Page<WeeklyStatus> weeklyStatuses = weeklyStatusService.findAllWeeklyStatuses(offset, pageSize);
		
		return ResponseEntity.ok(weeklyStatuses);
	}
	
	@PostMapping("/addBy=PROJECT_ID/project/{projectId}")
	public ResponseEntity<ApiResponse> addWeeklyStatus(@PathVariable Long projectId, @RequestBody @Valid WeeklyStatusRequest weeklyStatusRequest) throws ProjectNotFoundException {
		if(!projectService.checkIfProjectExistsById(projectId)) {
			throw new ProjectNotFoundException(String.format(Configurations.INVALID_PROJECT_ID, projectId));
		}
		
		LocalDate weeklyEndDate = LocalDate.parse(weeklyStatusRequest.getWeeklyEndDate());
		
		// Checking if the Weekly-Status already exists for the given Project ID and Weekly_End_Date
		Optional<WeeklyStatus> existingWeeklyStatus = weeklyStatusService.findWeeklyStatusByWeeklyEndDateAndByProjectId(projectId, weeklyEndDate);
		if(existingWeeklyStatus.isPresent()) {
			Long id = existingWeeklyStatus.get().getId();
			throw new WeeklyStatusAlreadyExistException(String.format(Configurations.WEEKLY_STATUS_ALREADY_EXIST, id, weeklyEndDate.toString(), projectId));
		}
		
		Optional<WeeklyStatus> objWeeklyStatus = weeklyStatusService.addWeeklyStatusByProjectId(projectId, weeklyStatusRequest);
		
		ApiResponse apiResponse = null;
		if(!objWeeklyStatus.isPresent()) {
			apiResponse = new ApiResponse(String.format(Configurations.WEEKLY_STATUS_ADD_ON_FAILED), HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		WeeklyStatus newWeeklyStatus = objWeeklyStatus.get();
		apiResponse = new ApiResponse(String.format(Configurations.WEEKLY_STATUS_ADD_ON_SUCCESS, newWeeklyStatus.getId()), HttpStatus.CREATED);
		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
	}
	
	@PutMapping("/updateBy=PROJECT_ID/project/{projectId}/weekly_status/{weeklyStatusId}")
	public ResponseEntity<ApiResponse> updateWeeklyStatus(@PathVariable Long projectId, @PathVariable Long weeklyStatusId, @RequestBody @Valid WeeklyStatusRequest weeklyStatusRequest) 
			throws WeeklyStatusNotFoundException, ProjectNotFoundException {
		if(!projectService.checkIfProjectExistsById(projectId)) {
			throw new ProjectNotFoundException(String.format(Configurations.INVALID_PROJECT_ID, projectId));
		}

		if(!weeklyStatusService.checkIfWeeklyStatusExistsById(weeklyStatusId)) {
			throw new WeeklyStatusNotFoundException(String.format(Configurations.INVALID_WEEKLY_STATUS_ID, weeklyStatusId));
		}
		
		WeeklyStatus updatedWeeklyStatuses = weeklyStatusService.updateWeeklyStatusByProjectId(projectId, weeklyStatusId, weeklyStatusRequest);
		
		ApiResponse apiResponse = null;
		if(null == updatedWeeklyStatuses) {
			apiResponse = new ApiResponse(String.format(Configurations.WEEKLY_STATUS_UPDATE_ON_FAILED), HttpStatus.INTERNAL_SERVER_ERROR);
			new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		apiResponse = new ApiResponse(String.format(Configurations.WEEKLY_STATUS_UPDATE_ON_SUCCESS, updatedWeeklyStatuses.getId()), HttpStatus.OK);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteBy=ID/project/{projectId}/weekly_status/{weeklyStatusId}")
	public ResponseEntity<ApiResponse> deleteWeeklyStatus(@PathVariable Long projectId, @PathVariable Long weeklyStatusId) 
			throws WeeklyStatusNotFoundException, ProjectNotFoundException {
		if(!projectService.checkIfProjectExistsById(projectId)) {
			throw new ProjectNotFoundException(String.format(Configurations.INVALID_PROJECT_ID, projectId));
		}

		if(!weeklyStatusService.checkIfWeeklyStatusExistsById(weeklyStatusId)) {
			throw new WeeklyStatusNotFoundException(String.format(Configurations.INVALID_WEEKLY_STATUS_ID, weeklyStatusId));
		}
		
		boolean isDeleted = weeklyStatusService.deleteWeeklyStatusByIdAndByProjectId(projectId, weeklyStatusId);
		
		ApiResponse apiResponse = null;
		if(!isDeleted) {
			apiResponse = new ApiResponse(String.format(Configurations.DELETE_WEEKLY_STATUS_ON_FAILED, projectId), HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		apiResponse = new ApiResponse(String.format(Configurations.DELETE_WEEKLY_STATUS_ON_SUCCESS, projectId), HttpStatus.OK);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteAllBy=PROJECT_ID/project/{projectId}")
	public ResponseEntity<ApiResponse> deleteAllWeeklyStatusesByProjectId(@PathVariable Long projectId) throws ProjectNotFoundException {
		if(!projectService.checkIfProjectExistsById(projectId)) {
			throw new ProjectNotFoundException(String.format(Configurations.INVALID_PROJECT_ID, projectId));
		}
		
		boolean isDeleted = weeklyStatusService.deleteAllWeeklyStatusesByProjectId(projectId);
		
		ApiResponse apiResponse = null;
		if(!isDeleted) {
			apiResponse = new ApiResponse(String.format(Configurations.DELETE_ALL_WEEKLY_STATUSES_BY_PROJECT_ON_FAILED, projectId), HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		apiResponse = new ApiResponse(String.format(Configurations.DELETE_ALL_WEEKLY_STATUSES_BY_PROJECT_ON_SUCCESS, projectId), HttpStatus.OK);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteAllBy=NONE")
	public ResponseEntity<ApiResponse> deleteAllWeeklyStatuses() {
		boolean isAllDeleted = weeklyStatusService.deleteAllWeeklyStatuses();
		
		ApiResponse apiResponse = null;
		if(!isAllDeleted) {
			apiResponse = new ApiResponse(String.format(Configurations.DELETE_ALL_WEEKLY_STATUSES_ON_FAILED), HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		apiResponse = new ApiResponse(String.format(Configurations.DELETE_ALL_WEEKLY_STATUSES_ON_SUCCESS), HttpStatus.OK);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

}
