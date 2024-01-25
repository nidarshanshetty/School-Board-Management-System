package com.school.sbm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sbm.reqeustdto.ScheduleRequest;
import com.school.sbm.responsedto.ScheduleResponse;
import com.school.sbm.service.IScheduleService;
import com.school.sbm.utility.ResponseStructure;


@RestController
public class ScheduleController 
{

	@Autowired
	private IScheduleService  iScheduleService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/schools/{schoolId}/schedules")
	public ResponseEntity<ResponseStructure<ScheduleResponse>>saveSchedule(@PathVariable int schoolId,@RequestBody ScheduleRequest scheduleRequest)
	{
		return iScheduleService.saveSchedule(schoolId,scheduleRequest);
	}

	@GetMapping("/schools/{schoolId}/schedules")
	public ResponseEntity<ResponseStructure<ScheduleResponse>>findSchedule(@PathVariable int schoolId)
	{
		return iScheduleService.findSchedule(schoolId);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/schedules/{scheduleId}")
	public ResponseEntity<ResponseStructure<ScheduleResponse>>updateSchedule(@PathVariable int scheduleId,@RequestBody ScheduleRequest scheduleRequest)
	{
		return iScheduleService.updateSchedule(scheduleId,scheduleRequest);
	}

}
