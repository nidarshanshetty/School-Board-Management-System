package com.school.sbm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sbm.reqeustdto.ClassHourRequest;
import com.school.sbm.responsedto.ClassHourResponse;
import com.school.sbm.service.IClassHourService;
import com.school.sbm.utility.ResponseStructure;

@RestController
public class ClassHourController 
{
	@Autowired
	private IClassHourService iClassHourService;

	@PostMapping("/academic-program/{programId}/class-hours")
	public ResponseEntity<ResponseStructure<List<ClassHourResponse>>>generateClassHourForAcademicProgram(@PathVariable int programId)
	{
		return iClassHourService.generateClassHourForAcademicProgram(programId);
	}
	@PutMapping("/class-hours")
	public ResponseEntity<ResponseStructure<List<ClassHourResponse>>> updateClassHour(@RequestBody List<ClassHourRequest> classHourRequests)
	{
		return iClassHourService.updateClassHour(classHourRequests);
	}

	@PutMapping("/academic-program/{programId}/class-hours")
	public ResponseEntity<ResponseStructure<List<ClassHourResponse>>>autoRepeatWeeklyTimeTable(@PathVariable int programId)
	{
		return iClassHourService.autoRepeatWeeklyTimeTable(programId);
	}
}

