package com.school.sbm.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.school.sbm.reqeustdto.AcademicProgramRequest;
import com.school.sbm.responsedto.AcademicProgramResponse;
import com.school.sbm.utility.ResponseStructure;

public interface IAcademicProgramService {

	ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveAcademicProgram(int schoolId,
			AcademicProgramRequest academicProgramRequest);

	ResponseEntity<ResponseStructure<List<AcademicProgramResponse>>> findAcademicProgram(int schoolId);

	ResponseEntity<ResponseStructure<AcademicProgramResponse>> deleteAcademicProgram(int programId);

}
