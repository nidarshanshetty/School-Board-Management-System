package com.school.sbm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sbm.reqeustdto.AcademicProgramRequest;
import com.school.sbm.responsedto.AcademicProgramResponse;
import com.school.sbm.service.IAcademicProgramService;
import com.school.sbm.utility.ResponseStructure;

@RestController
public class AcademicProgramController 
{
	@Autowired
	private IAcademicProgramService iAcademicProgramService;


	@PostMapping("/schools/{schoolId}/academic-programs")
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>>saveAcaAcademicProgram(@PathVariable int schoolId,@RequestBody AcademicProgramRequest academicProgramRequest)
	{
		return iAcademicProgramService.saveAcaAcademicProgram(schoolId,academicProgramRequest);
	}
}
