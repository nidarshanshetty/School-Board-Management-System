package com.school.sbm.utility;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.school.sbm.exception.AcademicProgamNotFoundException;
import com.school.sbm.exception.AdminAlreadyExistExceptoon;
import com.school.sbm.exception.AdminNotFoundException;
import com.school.sbm.exception.AdmineCannotBeAssignedToAcademicProgram;
import com.school.sbm.exception.OnlyTeacherCanBeAssignedToSubjectException;
import com.school.sbm.exception.ScheduleAlreadyExistException;
import com.school.sbm.exception.ScheduleObjectNotFoundException;
import com.school.sbm.exception.SchoolAlreadyExistException;
import com.school.sbm.exception.SchoolObjectNotFoundException;
import com.school.sbm.exception.SubjectNotFoundException;
import com.school.sbm.exception.UserObjectNotFoundException;

@RestControllerAdvice
public class ApplicationExceptionHandler 
{

	public ResponseEntity<Object> structure (HttpStatus status,String message,Object rootCause)
	{
		return new ResponseEntity<Object> (Map.of(
				"status",status.value(),
				"message",message,
				"rootCause",rootCause
				),status);
	}

	@ExceptionHandler(UserObjectNotFoundException.class)
	public ResponseEntity<Object> handleUserObjectNotFoundById(UserObjectNotFoundException ex)
	{
		return structure(HttpStatus.NOT_FOUND,ex.getMessage(),"user not found by the specified Id");
	}

	@ExceptionHandler(SchoolObjectNotFoundException.class)
	public ResponseEntity<Object> handleSchoolObjectNotFoundById(SchoolObjectNotFoundException ex)
	{
		return structure(HttpStatus.NOT_FOUND,ex.getMessage(),"school not found by  the specified Id");
	}

	@ExceptionHandler(AdminAlreadyExistExceptoon.class)
	public ResponseEntity<Object> handleAdminAlreadyExist(AdminAlreadyExistExceptoon ex)
	{
		return structure(HttpStatus.BAD_REQUEST, ex.getMessage(), "admin already existed");
	}

	@ExceptionHandler(AdminNotFoundException.class)
	public ResponseEntity<Object> handleAdminNotFound(AdminNotFoundException ex)
	{
		return structure(HttpStatus.NOT_FOUND, ex.getMessage(), "admin not existed");
	}

	@ExceptionHandler(SchoolAlreadyExistException.class)
	public ResponseEntity<Object> handleSchoolAlreadyExist(SchoolAlreadyExistException ex)
	{
		return structure(HttpStatus.FOUND,ex.getMessage(), "school already existed");
	}
	@ExceptionHandler(ScheduleAlreadyExistException.class)
	public ResponseEntity<Object> handleScheduleNotExist(ScheduleAlreadyExistException ex)
	{
		return structure(HttpStatus.FOUND, ex.getMessage(),"schedule already existed");
	}

	@ExceptionHandler(ScheduleObjectNotFoundException.class)
	public ResponseEntity<Object> handleScheduleObjectNotFoundById(ScheduleObjectNotFoundException ex)
	{
		return structure(HttpStatus.NOT_FOUND, ex.getMessage()," schedule not found by the specified Id" );
	}
	@ExceptionHandler(AcademicProgamNotFoundException.class)
	public ResponseEntity<Object> handleAcademicProgramNotFoundById(AcademicProgamNotFoundException ex)
	{
		return structure(HttpStatus.NOT_FOUND, ex.getMessage(), "academic Program Not Found By the specified Id");
	}
	@ExceptionHandler(AdmineCannotBeAssignedToAcademicProgram.class)
	public ResponseEntity<Object> handleAdmineCannotBeAssignedToAcademicProgram(AdmineCannotBeAssignedToAcademicProgram ex)
	{
		return structure(HttpStatus.BAD_REQUEST, ex.getMessage(), "admine cannot assign to academic program");
	}
	@ExceptionHandler(OnlyTeacherCanBeAssignedToSubjectException.class)
	public ResponseEntity<Object> handleOnlyTeacherCanBeAssignedToSubjectException(OnlyTeacherCanBeAssignedToSubjectException ex)
	{
		return structure(HttpStatus.BAD_REQUEST, ex.getMessage(), "only teacher can assign to the subject");
	}
	@ExceptionHandler(SubjectNotFoundException.class)
	public ResponseEntity<Object> handleSubjectNotFoundException(SubjectNotFoundException ex)
	{
		return structure(HttpStatus.NOT_FOUND,ex.getMessage(), "subject not found by the specified Id");
	}

}
