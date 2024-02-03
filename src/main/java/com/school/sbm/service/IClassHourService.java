package com.school.sbm.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.school.sbm.reqeustdto.ClassHourRequest;
import com.school.sbm.reqeustdto.ExcelRequest;
import com.school.sbm.responsedto.ClassHourResponse;
import com.school.sbm.utility.ResponseStructure;

public interface IClassHourService
{

	ResponseEntity<ResponseStructure<List<ClassHourResponse>>> generateClassHourForAcademicProgram(int programId);

	ResponseEntity<ResponseStructure<List<ClassHourResponse>>> updateClassHour(List<ClassHourRequest> classHourRequests);

	ResponseEntity<ResponseStructure<List<ClassHourResponse>>> autoRepeatWeeklyTimeTable(int programId);

	String writeExcelSheet(int programId, ExcelRequest excelRequest);

	ResponseEntity<?> writeToExcel(MultipartFile file, LocalDate fromDate, LocalDate toDate,int programId)throws Exception;


}
