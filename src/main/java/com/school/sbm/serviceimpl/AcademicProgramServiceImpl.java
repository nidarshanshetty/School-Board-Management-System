package com.school.sbm.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sbm.entity.AcademicProgram;
import com.school.sbm.entity.School;
import com.school.sbm.exception.SchoolObjectNotFoundException;
import com.school.sbm.repository.IAcademicProgramRepository;
import com.school.sbm.repository.ISchoolRepository;
import com.school.sbm.reqeustdto.AcademicProgramRequest;
import com.school.sbm.responsedto.AcademicProgramResponse;
import com.school.sbm.service.IAcademicProgramService;
import com.school.sbm.utility.ResponseStructure;

@Service
public class AcademicProgramServiceImpl implements IAcademicProgramService
{

	@Autowired
	private IAcademicProgramRepository iAcademicProgramRepository;


	@Autowired
	private ISchoolRepository iSchoolRepository;


	@Autowired
	private ResponseStructure<AcademicProgramResponse> responseStructure;


	private AcademicProgram mapToAcademicProgramRequest(AcademicProgramRequest academicProgramRequest)
	{
		return AcademicProgram.builder()
				.programType(academicProgramRequest.getProgramType())
				.programName(academicProgramRequest.getProgramName())
				.beginsAt(academicProgramRequest.getBeginsAt())
				.endsAt(academicProgramRequest.getEndsAt())
				.build();
	}

	private AcademicProgramResponse mapToAcademicProgramResponse(AcademicProgram academicProgram )
	{
		return AcademicProgramResponse.builder()
				.programId(academicProgram.getProgramId())
				.programType(academicProgram.getProgramType())
				.programName(academicProgram.getProgramName())
				.beginsAt(academicProgram.getBeginsAt())
				.endsAt(academicProgram.getEndsAt())
				.build();
	}



	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveAcaAcademicProgram(int schoolId,
			AcademicProgramRequest academicProgramRequest) {

		School school = iSchoolRepository.findById(schoolId)
				.orElseThrow(()-> new SchoolObjectNotFoundException("school not found"));

		AcademicProgram academicProgram = iAcademicProgramRepository.save(mapToAcademicProgramRequest(academicProgramRequest));
		school.getAList().add(academicProgram);
		academicProgram.setSchool(school);

		iSchoolRepository.save(school);
		iAcademicProgramRepository.save(academicProgram );

		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("AcademicProgram saved successfully");
		responseStructure.setData(mapToAcademicProgramResponse(academicProgram));

		return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(responseStructure,HttpStatus.CREATED);
	}

}