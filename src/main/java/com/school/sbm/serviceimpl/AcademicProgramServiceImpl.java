package com.school.sbm.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sbm.entity.AcademicProgram;
import com.school.sbm.entity.ClassHour;
import com.school.sbm.entity.School;
import com.school.sbm.entity.Subject;
import com.school.sbm.enums.ProgramType;
import com.school.sbm.exception.AcademicProgamNotFoundException;
import com.school.sbm.exception.SchoolObjectNotFoundException;
import com.school.sbm.repository.IAcademicProgramRepository;
import com.school.sbm.repository.IClassHourRepository;
import com.school.sbm.repository.ISchoolRepository;
import com.school.sbm.reqeustdto.AcademicProgramRequest;
import com.school.sbm.responsedto.AcademicProgramResponse;
import com.school.sbm.service.IAcademicProgramService;
import com.school.sbm.utility.ResponseStructure;

import jakarta.transaction.Transactional;

@Service
public class AcademicProgramServiceImpl implements IAcademicProgramService
{
	@Autowired
	private IClassHourRepository classHourRepository;

	@Autowired
	private IAcademicProgramRepository iAcademicProgramRepository;


	@Autowired
	private ISchoolRepository iSchoolRepository;


	@Autowired
	private ResponseStructure<AcademicProgramResponse> responseStructure;

	@Autowired
	private ResponseStructure<List<AcademicProgramResponse>> ListResponseStructure;


	private AcademicProgram mapToAcademicProgramRequest(AcademicProgramRequest academicProgramRequest)
	{
		return AcademicProgram.builder()
				.programType(ProgramType.valueOf(academicProgramRequest.getProgramType().toUpperCase()))
				.programName(academicProgramRequest.getProgramName())
				.beginsAt(academicProgramRequest.getBeginsAt())
				.endsAt(academicProgramRequest.getEndsAt())
				.build();
	}

	public AcademicProgramResponse mapToAcademicProgramResponse(AcademicProgram academicProgram )
	{
		List<String>subjects= new ArrayList<String>();
		List<Subject>listOfSubjects=academicProgram.getSubjects();

		if(listOfSubjects!=null)
		{
			listOfSubjects.forEach(sub->{
				subjects.add(sub.getSubjectNames());
			});
		}

		return AcademicProgramResponse.builder()
				.programId(academicProgram.getProgramId())
				.programType(academicProgram.getProgramType())
				.programName(academicProgram.getProgramName())
				.beginsAt(academicProgram.getBeginsAt())
				.endsAt(academicProgram.getEndsAt())
				.subjects(academicProgram.getSubjects())
				.build();
	}



	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveAcademicProgram(int schoolId,
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

	@Override
	public ResponseEntity<ResponseStructure<List<AcademicProgramResponse>>> findAcademicProgram(int schoolId)
	{
		iSchoolRepository.findById(schoolId)
		.orElseThrow(()-> new SchoolObjectNotFoundException("school not found by the specified id"));

		List<AcademicProgram> findAll = iAcademicProgramRepository.findAll();
		List<AcademicProgramResponse> collect = findAll.stream()
				.map(u->mapToAcademicProgramResponse(u))
				.collect(Collectors.toList());

		if(findAll.isEmpty())
		{
			ListResponseStructure.setStatus(HttpStatus.FOUND.value());
			ListResponseStructure.setMessage("there is no data present in academic program");
			ListResponseStructure.setData(collect);
		}

		ListResponseStructure.setStatus(HttpStatus.FOUND.value());
		ListResponseStructure.setMessage("AcademicProgram found successfully");
		ListResponseStructure.setData(collect);

		return new ResponseEntity<ResponseStructure<List<AcademicProgramResponse>>>(ListResponseStructure,HttpStatus.FOUND);
	}

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> deleteAcademicProgram(int programId)
	{
		AcademicProgram academicProgram = iAcademicProgramRepository.findById(programId)
				.orElseThrow(()->new AcademicProgamNotFoundException("academic program not found"));

		if(academicProgram.isDeleted()==true)
		{
			throw new AcademicProgamNotFoundException("academic program not found");
		}
		else
		{
			academicProgram.setDeleted(true);
			AcademicProgram save = iAcademicProgramRepository.save(academicProgram);

			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("academic program deleted successfully");
			responseStructure.setData(mapToAcademicProgramResponse(save));
			return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(responseStructure,HttpStatus.OK);
		}
	}
	@Transactional
	public void deleteSoftDeletedData()
	{
		List<AcademicProgram> academicPrograms = iAcademicProgramRepository.findByIsDeleted(true);
		for(AcademicProgram academicProgram:academicPrograms)
		{
			academicProgram.setSubjects(null);
			List<ClassHour> listOfClassHours = academicProgram.getListOfClassHours();
			for(ClassHour listHour:listOfClassHours)
			{
				classHourRepository.delete(listHour);;
			}
			iAcademicProgramRepository.delete(academicProgram);

		}
	}

}
