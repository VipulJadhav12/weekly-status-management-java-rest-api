package com.afourathon.weekly_status_management_rest_api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.afourathon.weekly_status_management_rest_api.data.entity.WeeklyStatus;
import com.afourathon.weekly_status_management_rest_api.data.payloads.request.WeeklyStatusRequest;

public interface WeeklyStatusService {
	
	// List of service fns to CHECK Weekly-Status
	public boolean checkIfWeeklyStatusExistsById(Long id);
	
	/* ========================================
	 * List of service fns to FIND Weekly-Status(es)
	 * based on conditions.
	 * ========================================
	 */
	public Optional<WeeklyStatus> findWeeklyStatusById(Long id);
	
	public Optional<WeeklyStatus> findWeeklyStatusByIdAndByProjectId(Long statusId, Long projectId);
	
	public Optional<WeeklyStatus> findWeeklyStatusByWeeklyEndDateAndByProjectId(Long projectId, LocalDate date);

	public List<WeeklyStatus> findAllWeeklyStatusesByProjectId(Long projectId);
	
	public Page<WeeklyStatus> findAllWeeklyStatusesByProjectId(Long projectId, int offset, int pageSize);
	
	public Page<WeeklyStatus> findAllWeeklyStatusesByProjectIdInDesc(Long projectId, int offset, int pageSize);

	public Page<WeeklyStatus> findAllWeeklyStatusesByWeeklyEndDate(LocalDate date, int offset, int pageSize);

	public List<WeeklyStatus> findAllWeeklyStatuses();

	public Page<WeeklyStatus> findAllWeeklyStatuses(int offset, int pageSize);
	
	/* ========================================
	 * List of service fns to CREATE and 
	 * UPDATE Weekly-Status(es).
	 * ========================================
	 */
	public Optional<WeeklyStatus> addWeeklyStatusByProjectId(Long projectId, WeeklyStatusRequest request);
	
	public WeeklyStatus updateWeeklyStatusByProjectId(Long projectId, Long statusId, WeeklyStatusRequest request);
	
	/* ========================================
	 * List of service fns to DELETE Weekly-Status(es).
	 * ========================================
	 */
	public boolean deleteWeeklyStatusByIdAndByProjectId(Long projectId, Long statusId);
	
	public boolean deleteAllWeeklyStatusesByProjectId(Long projectId);
	
	public boolean deleteAllWeeklyStatuses();



}
