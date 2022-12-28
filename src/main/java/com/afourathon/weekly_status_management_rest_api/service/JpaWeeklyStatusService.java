package com.afourathon.weekly_status_management_rest_api.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.afourathon.weekly_status_management_rest_api.data.entity.WeeklyStatus;
import com.afourathon.weekly_status_management_rest_api.data.payloads.repository.WeeklyStatusRepository;
import com.afourathon.weekly_status_management_rest_api.data.payloads.request.WeeklyStatusRequest;

@Service
public class JpaWeeklyStatusService implements WeeklyStatusService {

	@Autowired
	ProjectService projectService;

	@Autowired
	WeeklyStatusRepository weeklyStatusRepository;

	private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// List of service fns to CHECK Weekly-Status
	@Override
	public boolean checkIfWeeklyStatusExistsById(Long id) {
		return weeklyStatusRepository.existsById(id);
	}

	/* ========================================
	 * List of service fns to FIND Weekly-Status(es)
	 * based on conditions.
	 * ========================================
	 */

	@Override
	public Optional<WeeklyStatus> findWeeklyStatusById(Long id) {
		return weeklyStatusRepository.findById(id);
	}

	@Override
	public Optional<WeeklyStatus> findWeeklyStatusByIdAndByProjectId(Long statusId, Long projectId) {
		return weeklyStatusRepository.findWeeklyStatusByIdAndByProjectId(statusId, projectId);
	}

	@Override
	public Optional<WeeklyStatus> findWeeklyStatusByWeeklyEndDateAndByProjectId(Long projectId, LocalDate date) {
		return weeklyStatusRepository.findWeeklyStatusByWeeklyEndDateAndByProjectId(date, projectId);
	}

	@Override
	public List<WeeklyStatus> findAllWeeklyStatusesByProjectId(Long projectId) {
		return weeklyStatusRepository.findAllWeeklyStatusesByProjectId(projectId);
	}

	@Override
	public Page<WeeklyStatus> findAllWeeklyStatusesByProjectId(Long projectId, int offset, int pageSize) {
		return weeklyStatusRepository.findAllWeeklyStatusesByProjectId(projectId, PageRequest.of(offset, pageSize));
	}

	@Override
	public Page<WeeklyStatus> findAllWeeklyStatusesByProjectIdInDesc(Long projectId, int offset, int pageSize) {
		return weeklyStatusRepository.findAllWeeklyStatusesByProjectIdInDesc(projectId, PageRequest.of(offset, pageSize));
	}

	@Override
	public Page<WeeklyStatus> findAllWeeklyStatusesByWeeklyEndDate(LocalDate date, int offset, int pageSize) {
		return weeklyStatusRepository.findAllWeeklyStatusesByWeeklyEndDate(date, PageRequest.of(offset, pageSize));
	}

	@Override
	public List<WeeklyStatus> findAllWeeklyStatuses() {
		return weeklyStatusRepository.findAll(Sort.by(Sort.Direction.ASC, "weeklyEndDate"));
	}

	@Override
	public Page<WeeklyStatus> findAllWeeklyStatuses(int offset, int pageSize) {
		return weeklyStatusRepository.findAll(PageRequest.of(offset, pageSize));
	}

	/* ========================================
	 * List of service fns to CREATE and 
	 * UPDATE Weekly-Status(es).
	 * ========================================
	 */

	@Override
	public Optional<WeeklyStatus> addWeeklyStatusByProjectId(Long projectId, WeeklyStatusRequest request) {
		LocalDate weeklyEndDate = LocalDate.parse(request.getWeeklyEndDate());

		// Create a new Weekly-Status entity to be saved
		WeeklyStatus weeklyStatus = new WeeklyStatus();
		weeklyStatus.setStatus(request.getStatus());
		weeklyStatus.setHighlight(request.getHighlight());
		weeklyStatus.setRisk(request.getRisk());
		weeklyStatus.setWeeklyEndDate(weeklyEndDate);

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		weeklyStatus.setCreatedOn(LocalDateTime.parse(SIMPLE_DATE_FORMAT.format(timestamp), DATETIME_FORMAT));
		weeklyStatus.setLastModifiedOn(LocalDateTime.parse(SIMPLE_DATE_FORMAT.format(timestamp), DATETIME_FORMAT));

		// Assign the newly created Weekly-Status entity to the given Project ID
		projectService.assignWeeklyStatusToProject(projectId, weeklyStatus);

		// Retrieve and return the newly created/assigned Weekly-Status entity
		return weeklyStatusRepository.findWeeklyStatusByWeeklyEndDateAndByProjectId(weeklyEndDate, projectId);
	}

	@Override
	public WeeklyStatus updateWeeklyStatusByProjectId(Long projectId, Long statusId, WeeklyStatusRequest request) {
		// Find the Weekly-Status by ID and Project ID
		Optional<WeeklyStatus> objWeeklyStatus = weeklyStatusRepository.findWeeklyStatusByIdAndByProjectId(statusId, projectId);

		// Check if the Weekly-Status entity is present
		if(objWeeklyStatus.isPresent()) {
			// Get the actual Weekly-Status entity
			WeeklyStatus weeklyStatus = objWeeklyStatus.get();

			// Set the Weekly-Status attributes with the updated attributes fetched through the WeeklyStatusRequest body
			weeklyStatus.setStatus(request.getStatus());
			weeklyStatus.setHighlight(request.getHighlight());
			weeklyStatus.setRisk(request.getRisk());
			weeklyStatus.setWeeklyEndDate(LocalDate.parse(request.getWeeklyEndDate()));

			// Set the Last_Modified_On timestamp for the Weekly-Status entity to be updated
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			weeklyStatus.setLastModifiedOn(LocalDateTime.parse(SIMPLE_DATE_FORMAT.format(timestamp), DATETIME_FORMAT));

			// Assign and fetch the updated Weekly-Status based on Project ID
			return projectService.assignUpdatedWeeklyStatusToProject(projectId, statusId, weeklyStatus);
		}

		return null;
	}

	/* ========================================
	 * List of service fns to DELETE Weekly-Status(es).
	 * ========================================
	 */

	@Transactional
	@Override
	public boolean deleteWeeklyStatusByIdAndByProjectId(Long projectId, Long statusId) {
		try {
			// Delete the Weekly-Status by ID
			weeklyStatusRepository.deleteById(statusId);
			
			// Try to find and retrieve the Weekly-Status with the same ID
			Optional<WeeklyStatus> objWeeklyStatus = weeklyStatusRepository.findById(statusId);
			
			// Check and verify if the Weekly-Status with the same ID is present
			// If not found then the Weekly-Status entity with the given ID has been DELETED
			if(!objWeeklyStatus.isPresent())
				return true;
		}
		catch(IllegalArgumentException ex) {
			return false;
		}

		return false;
	}

	@Transactional
	@Override
	public boolean deleteAllWeeklyStatusesByProjectId(Long projectId) {
		// Delete all Weekly-Status(es) by Project ID
		weeklyStatusRepository.deleteAllWeeklyStatusesByProjectId(projectId);

		// Retrieve the count of all Weekly-Status(es) by Project ID
		// which should be equal to 0
		long count = weeklyStatusRepository.countOfAllWeeklyStatusesByProjectId(projectId);

		// Check and verify if the count of all Weekly-Status(es) assigned to given Project ID is 0
		if(0 == count)
			return true;

		return false;
	}

	@Transactional
	@Override
	public boolean deleteAllWeeklyStatuses() {
		// Delete all Weekly-Status(es) from the database
		weeklyStatusRepository.deleteAll();

		// Check and verify if the count of all Weekly-Status(es) is 0
		if(0 == weeklyStatusRepository.count())
			return true;

		return false;
	}

}
