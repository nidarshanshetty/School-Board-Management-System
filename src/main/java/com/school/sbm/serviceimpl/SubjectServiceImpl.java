package com.school.sbm.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sbm.entity.Subject;
import com.school.sbm.exception.AcademicProgamNotFoundException;
import com.school.sbm.repository.IAcademicProgramRepository;
import com.school.sbm.repository.ISubjectRepository;
import com.school.sbm.reqeustdto.SubjectRequest;
import com.school.sbm.responsedto.AcademicProgramResponse;
import com.school.sbm.service.ISubjectService;
import com.school.sbm.utility.ResponseStructure;

@Service
public class SubjectServiceImpl implements ISubjectService
{
	@Autowired
	private IAcademicProgramRepository iAcademicProgramRepository;

	@Autowired
	private ISubjectRepository iSubjectRepository;

	@Autowired
	private ResponseStructure<AcademicProgramResponse> responseStructure;

	@Autowired
	private AcademicProgramServiceImpl academicProgramServiceImpl;

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> addSubject(int programId,
			SubjectRequest subjectRequest)
	{
		return iAcademicProgramRepository.findById(programId).map(program->{ //found academic program
			List<Subject>subjects= (program.getSubjects()!= null)?program.getSubjects(): new ArrayList<Subject>();

			//to add new subjects that are specified by the client
			subjectRequest.getSubjectNames().forEach(name->{
				boolean isPresent =false;
				for(Subject subject:subjects) {
					isPresent = (name.equalsIgnoreCase(subject.getSubjectNames()))?true:false;
					if(isPresent)break;
				}
				if(!isPresent)subjects.add(iSubjectRepository.findBySubjectNames(name)
						.orElseGet(()-> iSubjectRepository.save(Subject.builder().subjectNames(name).build())));
			});
			//to remove the subjects that are not specified by the client
			List<Subject>toBeRemoved= new ArrayList<Subject>();
			subjects.forEach(subject->{
				boolean isPresent = false;
				for(String name:subjectRequest.getSubjectNames()) {
					isPresent=(subject.getSubjectNames().equalsIgnoreCase(name))?true :false;
					if(!isPresent)break;
				}
				if(!isPresent)toBeRemoved.add(subject);
			});
			subjects.removeAll(toBeRemoved);


			program.setSubjects(subjects);//set subjects list to the academic program
			iAcademicProgramRepository.save(program);//saving updated program to the database
			responseStructure.setStatus(HttpStatus.CREATED.value());
			responseStructure.setMessage("updated the subject list to academic program");
			responseStructure.setData(academicProgramServiceImpl.mapToAcademicProgramResponse(program));
			return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(responseStructure,HttpStatus.CREATED);
		}).orElseThrow(()-> new AcademicProgamNotFoundException("AcademicProgram not found"));

	}

}
