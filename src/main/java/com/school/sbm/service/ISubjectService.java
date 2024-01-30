package com.school.sbm.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.school.sbm.reqeustdto.SubjectRequest;
import com.school.sbm.responsedto.AcademicProgramResponse;
import com.school.sbm.responsedto.SubjectResponse;
import com.school.sbm.utility.ResponseStructure;

public interface ISubjectService {

	ResponseEntity<ResponseStructure<AcademicProgramResponse>> addSubject(int programId, SubjectRequest subjectRequest);

	ResponseEntity<ResponseStructure<List<SubjectResponse>>> findAllSubjects();




}
