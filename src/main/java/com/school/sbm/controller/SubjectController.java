package com.school.sbm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sbm.reqeustdto.SubjectRequest;
import com.school.sbm.responsedto.AcademicProgramResponse;
import com.school.sbm.service.ISubjectService;
import com.school.sbm.utility.ResponseStructure;

@RestController
public class SubjectController 
{

	@Autowired
	private ISubjectService iSubjectService;

	@PostMapping("/academic-programs/{programId}/subjects")
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>>addSubject(@PathVariable int programId,@RequestBody SubjectRequest subjectRequest)
	{
		return iSubjectService.addSubject(programId,subjectRequest);
	}
}
