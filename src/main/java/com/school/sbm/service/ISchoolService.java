package com.school.sbm.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.school.sbm.reqeustdto.SchoolRequest;
import com.school.sbm.responsedto.SchoolResponse;
import com.school.sbm.utility.ResponseStructure;

public interface ISchoolService
{

	ResponseEntity<ResponseStructure<SchoolResponse>> saveSchool(Integer userId, SchoolRequest school);

	ResponseEntity<ResponseStructure<SchoolResponse>> updateSchool(int schoolId, SchoolRequest school);

	ResponseEntity<ResponseStructure<List<SchoolResponse>>> findAllSchool();

	ResponseEntity<ResponseStructure<SchoolResponse>> deleteSchool(int schoolId);

	ResponseEntity<ResponseStructure<SchoolResponse>> findSchoolById(int schoolId);



}
