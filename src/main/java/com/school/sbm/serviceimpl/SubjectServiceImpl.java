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
		return iAcademicProgramRepository.findById(programId).map(program->{
			List<Subject>subjects=new ArrayList<Subject>();
			subjectRequest.getSubjectNames().forEach(name->{

				Subject subject = iSubjectRepository.findBySubjectNames(name).map(s->s).orElseGet(()->{
					Subject subject2 =new  Subject();
					subject2.setSubjectNames(name);
					iSubjectRepository.save(subject2);

					return subject2;
				});
				subjects.add(subject);
			});
			program.setSubjects(subjects);
			iAcademicProgramRepository.save(program);
			responseStructure.setStatus(HttpStatus.CREATED.value());
			responseStructure.setMessage("updated the subject list to academic program");
			responseStructure.setData(academicProgramServiceImpl.mapToAcademicProgramResponse(program));
			return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(responseStructure,HttpStatus.CREATED);
		}).orElseThrow(()-> new AcademicProgamNotFoundException("AcademicProgram not found"));

	}

}
