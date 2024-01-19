package com.school.sbm.utility;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.school.sbm.exception.AcademicProgamNotFoundException;
import com.school.sbm.exception.AdminAlreadyExistExceptoon;
import com.school.sbm.exception.AdminNotFoundException;
import com.school.sbm.exception.ScheduleAlreadyExistException;
import com.school.sbm.exception.ScheduleObjectNotFoundException;
import com.school.sbm.exception.SchoolAlreadyExistException;
import com.school.sbm.exception.SchoolObjectNotFoundException;
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
	public ResponseEntity<Object> handleuserObjectNotFoundById(UserObjectNotFoundException ex)
	{
		return structure(HttpStatus.NOT_FOUND,ex.getMessage(),"user not found by the specified Id");
	}

	@ExceptionHandler(SchoolObjectNotFoundException.class)
	public ResponseEntity<Object> handleschoolObjectNotFoundById(SchoolObjectNotFoundException ex)
	{
		return structure(HttpStatus.NOT_FOUND,ex.getMessage(),"school not found by  the specified Id");
	}

	@ExceptionHandler(AdminAlreadyExistExceptoon.class)
	public ResponseEntity<Object> handleadminAlreadyExist(AdminAlreadyExistExceptoon ex)
	{
		return structure(HttpStatus.BAD_REQUEST, ex.getMessage(), "admin already exist");
	}

	@ExceptionHandler(AdminNotFoundException.class)
	public ResponseEntity<Object> handleadminNotFound(AdminNotFoundException ex)
	{
		return structure(HttpStatus.NOT_FOUND, ex.getMessage(), "admin not existed");
	}

	@ExceptionHandler(SchoolAlreadyExistException.class)
	public ResponseEntity<Object> handleschoolAlreadyExist(SchoolAlreadyExistException ex)
	{
		return structure(HttpStatus.FOUND,ex.getMessage(), "school already exist");
	}
	@ExceptionHandler(ScheduleAlreadyExistException.class)
	public ResponseEntity<Object> handlescheduleNotExist(ScheduleAlreadyExistException ex)
	{
		return structure(HttpStatus.FOUND, ex.getMessage(),"schedule already exist");
	}

	@ExceptionHandler(ScheduleObjectNotFoundException.class)
	public ResponseEntity<Object> handlescheduleObjectNotFoundById(ScheduleObjectNotFoundException ex)
	{
		return structure(HttpStatus.NOT_FOUND, ex.getMessage()," schedule not found by the specified Id" );
	}
	@ExceptionHandler(AcademicProgamNotFoundException.class)
	public ResponseEntity<Object> handleacademicProgramNotFoundById(AcademicProgamNotFoundException ex)
	{
		return structure(HttpStatus.NOT_FOUND, ex.getMessage(), "academic Program Not Found By the specified Id");
	}
}
