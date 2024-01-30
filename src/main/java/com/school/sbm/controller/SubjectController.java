package com.school.sbm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sbm.reqeustdto.SubjectRequest;
import com.school.sbm.responsedto.AcademicProgramResponse;
import com.school.sbm.responsedto.SubjectResponse;
import com.school.sbm.service.ISubjectService;
import com.school.sbm.utility.ResponseStructure;

@RestController
public class SubjectController 
{

	@Autowired
	private ISubjectService iSubjectService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/academic-programs/{programId}/subjects")
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>>addSubject(@PathVariable int programId,@RequestBody SubjectRequest subjectRequest)
	{
		return iSubjectService.addSubject(programId,subjectRequest);
	}

	@GetMapping("/subjects")
	public ResponseEntity<ResponseStructure<List<SubjectResponse>>>findAllSubjects()
	{
		return iSubjectService.findAllSubjects();
	}

}
