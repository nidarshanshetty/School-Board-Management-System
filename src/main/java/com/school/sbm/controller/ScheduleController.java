package com.school.sbm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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


	@PostMapping("/schools/{schoolId}/schedules")
	public ResponseEntity<ResponseStructure<ScheduleResponse>>saveSchedule(@PathVariable int schoolId,@RequestBody ScheduleRequest scheduleRequest)
	{
		return iScheduleService.saveSchedule(schoolId,scheduleRequest);
	}
}
