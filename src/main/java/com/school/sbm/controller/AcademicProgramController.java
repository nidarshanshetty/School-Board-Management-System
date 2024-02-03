package com.school.sbm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sbm.reqeustdto.AcademicProgramRequest;
import com.school.sbm.responsedto.AcademicProgramResponse;
import com.school.sbm.service.IAcademicProgramService;
import com.school.sbm.utility.ResponseStructure;

import jakarta.validation.Valid;

@RestController
public class AcademicProgramController 
{
	@Autowired
	private IAcademicProgramService iAcademicProgramService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/schools/{schoolId}/academic-programs")
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>>saveAcademicProgram(@PathVariable int schoolId,@RequestBody  @Valid AcademicProgramRequest academicProgramRequest)
	{
		return iAcademicProgramService.saveAcademicProgram(schoolId,academicProgramRequest);
	}

	@GetMapping("/schools/{schoolId}/academic-programs")
	public ResponseEntity<ResponseStructure<List<AcademicProgramResponse>>>findAcademicProgram(@PathVariable int schoolId)
	{
		return iAcademicProgramService.findAcademicProgram(schoolId);
	}

	@DeleteMapping("/academic-programs/{programId}")
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>>deleteAcademicProgram(@PathVariable int programId)
	{
		return iAcademicProgramService.deleteAcademicProgram(programId);
	}

}
