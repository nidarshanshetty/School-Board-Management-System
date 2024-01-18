package com.school.sbm.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.school.sbm.reqeustdto.AcademicProgramRequest;
import com.school.sbm.responsedto.AcademicProgramResponse;
import com.school.sbm.utility.ResponseStructure;

public interface IAcademicProgramService {

	ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveAcaAcademicProgram(int schoolId,
			AcademicProgramRequest academicProgramRequest);

	ResponseEntity<ResponseStructure<List<AcademicProgramResponse>>> findAcaAcademicProgram(int schoolId);

}
