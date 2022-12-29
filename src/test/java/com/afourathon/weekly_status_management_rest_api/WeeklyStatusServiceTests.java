package com.afourathon.weekly_status_management_rest_api;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.afourathon.weekly_status_management_rest_api.data.entity.Project;
import com.afourathon.weekly_status_management_rest_api.data.entity.WeeklyStatus;
import com.afourathon.weekly_status_management_rest_api.data.payloads.repository.ProjectRepository;
import com.afourathon.weekly_status_management_rest_api.data.payloads.repository.WeeklyStatusRepository;
import com.afourathon.weekly_status_management_rest_api.data.payloads.request.WeeklyStatusRequest;
import com.afourathon.weekly_status_management_rest_api.service.JpaProjectService;
import com.afourathon.weekly_status_management_rest_api.service.JpaWeeklyStatusService;
import com.afourathon.weekly_status_management_rest_api.service.ProjectService;
import com.afourathon.weekly_status_management_rest_api.service.WeeklyStatusService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class WeeklyStatusServiceTests {

	@InjectMocks
	private WeeklyStatusService weeklyStatusService = new JpaWeeklyStatusService();

	@Mock
	private WeeklyStatusRepository weeklyStatusRepository;

	@InjectMocks
	private ProjectService projectService = new JpaProjectService();

	@Mock
	private ProjectRepository projectRepository;

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Test
	public void findWeeklyStatusByIdTest() {
		Long weeklyStatusId = 101L;

		WeeklyStatus weeklyStatus = new WeeklyStatus();
		weeklyStatus.setId(weeklyStatusId);
		weeklyStatus.setStatus("Demo Status #1");
		weeklyStatus.setHighlight("Demo Highlight #1");
		weeklyStatus.setRisk("Demo Risk #1");
		weeklyStatus.setWeeklyEndDate(LocalDate.parse(new String("2022-01-01")));
		weeklyStatus.setCreatedOn(LocalDateTime.parse(new String("2022-01-01 12:15:30"), DATE_FORMAT));
		weeklyStatus.setLastModifiedOn(LocalDateTime.parse(new String("2022-01-01 12:15:30"), DATE_FORMAT));

		Optional<WeeklyStatus> objWeeklyStatus = Optional.of(weeklyStatus);

		when(weeklyStatusRepository.findById(weeklyStatusId)).thenReturn(objWeeklyStatus);

		assertTrue(weeklyStatusService.findWeeklyStatusById(weeklyStatusId).isPresent());
	}

	@Test
	public void findWeeklyStatusByIdAndByProjectIdTest() {
		Long weeklyStatusId = 101L;
		Long projectId = 1001L;

		WeeklyStatus weeklyStatus = new WeeklyStatus();
		weeklyStatus.setId(weeklyStatusId);
		weeklyStatus.setStatus("Demo Status #1");
		weeklyStatus.setHighlight("Demo Highlight #1");
		weeklyStatus.setRisk("Demo Risk #1");
		weeklyStatus.setWeeklyEndDate(LocalDate.parse(new String("2022-01-01")));
		weeklyStatus.setCreatedOn(LocalDateTime.parse(new String("2022-01-01 12:15:30"), DATE_FORMAT));
		weeklyStatus.setLastModifiedOn(LocalDateTime.parse(new String("2022-01-01 12:15:30"), DATE_FORMAT));

		Optional<WeeklyStatus> objWeeklyStatus = Optional.of(weeklyStatus);

		when(weeklyStatusRepository.findWeeklyStatusByIdAndByProjectId(weeklyStatusId, projectId)).thenReturn(objWeeklyStatus);

		assertTrue(weeklyStatusService.findWeeklyStatusByIdAndByProjectId(weeklyStatusId, projectId).isPresent());
	}

	@Test
	public void findAllWeeklyStatusesByProjectIdTest() {
		int offset = 0;
		int pageSize = 5;

		Long weeklyStatusId = 101L;
		Long projectId = 1001L;

		WeeklyStatus weeklyStatus = new WeeklyStatus();
		weeklyStatus.setId(weeklyStatusId);
		weeklyStatus.setStatus("Demo Status #1");
		weeklyStatus.setHighlight("Demo Highlight #1");
		weeklyStatus.setRisk("Demo Risk #1");
		weeklyStatus.setWeeklyEndDate(LocalDate.parse(new String("2022-01-01")));
		weeklyStatus.setCreatedOn(LocalDateTime.parse(new String("2022-01-01 12:15:30"), DATE_FORMAT));
		weeklyStatus.setLastModifiedOn(LocalDateTime.parse(new String("2022-01-01 12:15:30"), DATE_FORMAT));

		Page<WeeklyStatus> pageableWeeklyStatusList = new PageImpl<>(Stream.of(weeklyStatus)
				.collect(Collectors.toList()));

		when(weeklyStatusRepository.findAllWeeklyStatusesByProjectId(projectId, PageRequest.of(offset, pageSize))).thenReturn(pageableWeeklyStatusList);

		assertTrue(weeklyStatusService.findAllWeeklyStatusesByProjectId(projectId, offset, pageSize).stream().count() == 1);
	}

	@Test
	public void addWeeklyStatusByProjectIdTest() {
		// Testing Project
		Long projectId = 1001L;

		Project project = new Project();
		project.setId(projectId);
		project.setName("Test Spring Boot Project");
		project.setStartDate(LocalDate.parse(new String("2022-01-01")));
		project.setEndDate(LocalDate.parse(new String("2022-12-31")));
		project.setManagerName("John Smith");
		project.setManagerEmail("john.smith@myorg.com");

		WeeklyStatus existingWeeklyStatus = new WeeklyStatus();

		project.setWeeklyStatuses(Stream.of(existingWeeklyStatus)
				.collect(Collectors.toList()));

		Optional<Project> objProject = Optional.of(project);

		when(projectRepository.findById(projectId)).thenReturn(objProject);

		assertTrue(projectService.findProjectById(projectId).isPresent());

		if(objProject.isPresent()) {
			Project existingProject = objProject.get();

			// Testing WeeklyStatus
			Long weeklyStatusId = 101L;

			WeeklyStatusRequest weeklyStatusRequest = new WeeklyStatusRequest("Demo Status #1", "Demo Highlight #1", "Demo Risk #1", 
					new String("2022-01-01"));

			WeeklyStatus weeklyStatusToBeAdded = new WeeklyStatus();
			weeklyStatusToBeAdded.setId(weeklyStatusId);
			weeklyStatusToBeAdded.setStatus(weeklyStatusRequest.getStatus());
			weeklyStatusToBeAdded.setHighlight(weeklyStatusRequest.getHighlight());
			weeklyStatusToBeAdded.setRisk(weeklyStatusRequest.getRisk());
			weeklyStatusToBeAdded.setWeeklyEndDate(LocalDate.parse(weeklyStatusRequest.getWeeklyEndDate()));
			weeklyStatusToBeAdded.setCreatedOn(LocalDateTime.parse(new String("2022-01-01 12:15:30"), DATE_FORMAT));
			weeklyStatusToBeAdded.setLastModifiedOn(LocalDateTime.parse(new String("2022-01-01 12:15:30"), DATE_FORMAT));

			existingProject.getWeeklyStatuses().add(weeklyStatusToBeAdded);

			when(mock(ProjectService.class).assignWeeklyStatusToProject(projectId, weeklyStatusToBeAdded)).thenReturn(existingProject).getMock();

			assertTrue("Weekly_Status with ID: " + weeklyStatusId + " has been added successfully to Project with ID: " + projectId, existingProject.getWeeklyStatuses().size() == 2);
		}
	}

	@Test
	public void updateWeeklyStatusByProjectIdTest() {
		// Testing Project
		Long weeklyStatusId = 101L;
		Long projectId = 1001L;

		Project project = new Project();
		project.setId(projectId);
		project.setName("Test Spring Boot Project");
		project.setStartDate(LocalDate.parse(new String("2022-01-01")));
		project.setEndDate(LocalDate.parse(new String("2022-12-31")));
		project.setManagerName("John Smith");
		project.setManagerEmail("john.smith@myorg.com");

		WeeklyStatus weeklyStatus = new WeeklyStatus();
		weeklyStatus.setId(weeklyStatusId);
		weeklyStatus.setStatus("Demo Status #1");
		weeklyStatus.setHighlight("Demo Highlight #1");
		weeklyStatus.setRisk("Demo Risk #1");
		weeklyStatus.setWeeklyEndDate(LocalDate.parse(new String("2022-01-01")));
		weeklyStatus.setCreatedOn(LocalDateTime.parse(new String("2022-01-01 12:15:30"), DATE_FORMAT));
		weeklyStatus.setLastModifiedOn(LocalDateTime.parse(new String("2022-01-01 12:15:30"), DATE_FORMAT));

		project.setWeeklyStatuses(Stream.of(weeklyStatus)
				.collect(Collectors.toList()));

		Optional<Project> objProject = Optional.of(project);

		when(projectRepository.findById(projectId)).thenReturn(objProject);

		assertTrue(projectService.findProjectById(projectId).isPresent());

		if(objProject.isPresent()) {
			Optional<WeeklyStatus> objWeeklyStatus = Optional.of(weeklyStatus);
			when(weeklyStatusRepository.findWeeklyStatusByIdAndByProjectId(weeklyStatusId, projectId)).thenReturn(objWeeklyStatus);
			assertTrue(weeklyStatusService.findWeeklyStatusByIdAndByProjectId(weeklyStatusId, projectId).isPresent());

			if(objWeeklyStatus.isPresent()) {
				WeeklyStatus existingWeeklyStatus = objWeeklyStatus.get();

				WeeklyStatusRequest weeklyStatusRequest = new WeeklyStatusRequest("Updated Demo Status #1", "Updated Demo Highlight #1", "Updated Demo Risk #1", 
						new String("2022-01-01"));

				WeeklyStatus updatedWeeklyStatus = existingWeeklyStatus;
				updatedWeeklyStatus.setStatus(weeklyStatusRequest.getStatus());
				updatedWeeklyStatus.setHighlight(weeklyStatusRequest.getHighlight());
				updatedWeeklyStatus.setRisk(weeklyStatusRequest.getRisk());
				updatedWeeklyStatus.setLastModifiedOn(LocalDateTime.parse(new String("2022-01-01 02:15:30"), DATE_FORMAT));

				when(mock(ProjectService.class).assignUpdatedWeeklyStatusToProject(projectId, weeklyStatusId, existingWeeklyStatus)).thenReturn(updatedWeeklyStatus).getMock();

				assertTrue(updatedWeeklyStatus.getStatus().equalsIgnoreCase(weeklyStatusRequest.getStatus()));
				assertTrue(updatedWeeklyStatus.getHighlight().equalsIgnoreCase(weeklyStatusRequest.getHighlight()));
				assertTrue(updatedWeeklyStatus.getRisk().equalsIgnoreCase(weeklyStatusRequest.getRisk()));
				assertTrue(updatedWeeklyStatus.getLastModifiedOn().isEqual(LocalDateTime.parse(new String("2022-01-01 02:15:30"), DATE_FORMAT)));
			}
		}
	}

	@Test
	public void deleteWeeklyStatusByIdAndByProjectIdTest() {
		Long weeklyStatusId = 101L;
		Long projectId = 1001L;

		weeklyStatusService.deleteWeeklyStatusByIdAndByProjectId(projectId, weeklyStatusId);

		verify(weeklyStatusRepository, times(1)).deleteById(weeklyStatusId);
	}

	@Test
	public void deleteAllWeeklyStatusesByProjectIdTest() {
		Long projectId = 1001L;

		weeklyStatusService.deleteAllWeeklyStatusesByProjectId(projectId);

		verify(weeklyStatusRepository, times(1)).deleteAllWeeklyStatusesByProjectId(projectId);
	}

	@Test
	public void deleteAllWeeklyStatusesTest() {
		weeklyStatusService.deleteAllWeeklyStatuses();

		verify(weeklyStatusRepository, times(1)).deleteAll();;
	}
}
