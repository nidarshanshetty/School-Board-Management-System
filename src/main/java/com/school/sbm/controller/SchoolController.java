package com.school.sbm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sbm.reqeustdto.SchoolRequest;
import com.school.sbm.responsedto.SchoolResponse;
import com.school.sbm.service.ISchoolService;
import com.school.sbm.utility.ResponseStructure;

@RestController
public class SchoolController 
{

	@Autowired
	private ISchoolService iSchoolService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/users/{userId}/schools")
	public ResponseEntity<ResponseStructure<SchoolResponse>> saveSchool(@PathVariable Integer userId,@RequestBody  SchoolRequest school)
	{
		return iSchoolService.saveSchool(userId,school);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/schools/{schoolId}")
	public ResponseEntity<ResponseStructure<SchoolResponse>> updateSchool(@PathVariable int schoolId,@RequestBody SchoolRequest school)
	{
		return iSchoolService.updateSchool(schoolId,school);
	}

	@GetMapping("/schools")
	public ResponseEntity<ResponseStructure<List<SchoolResponse>>> findAllSchool()
	{
		return iSchoolService.findAllSchool();
	}


	@DeleteMapping("/schools/{schoolId}")
	public ResponseEntity<ResponseStructure<SchoolResponse>> deleteSchool(@PathVariable int schoolId)
	{
		return iSchoolService.deleteSchool(schoolId);
	}
	@GetMapping("/schools/{schoolId}")
	public ResponseEntity<ResponseStructure<SchoolResponse>> findSchoolById(@PathVariable int schoolId)
	{
		return iSchoolService.findSchoolById(schoolId);
	}


}
