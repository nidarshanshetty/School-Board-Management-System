package com.school.sbm.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.school.sbm.reqeustdto.ClassHourRequest;
import com.school.sbm.responsedto.ClassHourResponse;
import com.school.sbm.utility.ResponseStructure;

public interface IClassHourService
{

	ResponseEntity<ResponseStructure<List<ClassHourResponse>>> generateClassHourForAcademicProgram(int programId);

	ResponseEntity<ResponseStructure<List<ClassHourResponse>>> updateClassHour(List<ClassHourRequest> classHourRequests);

}