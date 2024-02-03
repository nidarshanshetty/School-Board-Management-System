package com.school.sbm.utility;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.school.sbm.exception.AcademicProgamNotFoundException;
import com.school.sbm.exception.AdminAlreadyExistExceptoon;
import com.school.sbm.exception.AdminNotFoundException;
import com.school.sbm.exception.AdmineCannotBeAssignedToAcademicProgramException;
import com.school.sbm.exception.ClassHourAlreadyGeneratedException;
import com.school.sbm.exception.CurrentClassHourEmptyException;
import com.school.sbm.exception.InvalidBreakTimeException;
import com.school.sbm.exception.InvalidClassHourEndException;
import com.school.sbm.exception.InvalidLunchTimeException;
import com.school.sbm.exception.InvalidOpenTimeAndCloseTimeException;
import com.school.sbm.exception.NoAssociatedObjectFoundException;
import com.school.sbm.exception.OnlyTeacherCanBeAssignedToSubjectException;
import com.school.sbm.exception.RoomNumberAlreadyExistedException;
import com.school.sbm.exception.ScheduleAlreadyExistException;
import com.school.sbm.exception.ScheduleObjectNotFoundException;
import com.school.sbm.exception.SchoolAlreadyExistException;
import com.school.sbm.exception.SchoolObjectNotFoundException;
import com.school.sbm.exception.StudentCannotBeAssignedToAcademicProgramException;
import com.school.sbm.exception.SubjectNotFoundException;
import com.school.sbm.exception.UserObjectNotFoundException;
import com.school.sbm.exception.UserRoleIsNotExistedException;

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
	@ExceptionHandler(AdmineCannotBeAssignedToAcademicProgramException.class)
	public ResponseEntity<Object> handleAdmineCannotBeAssignedToAcademicProgram(AdmineCannotBeAssignedToAcademicProgramException ex)
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
	@ExceptionHandler(StudentCannotBeAssignedToAcademicProgramException.class)
	public ResponseEntity<Object> handleStudentCannotBeAssignedToAcademicProgramException(StudentCannotBeAssignedToAcademicProgramException ex)
	{
		return structure(HttpStatus.BAD_REQUEST,ex.getMessage() ,"student cannot assign to academic program");
	}
	@ExceptionHandler(NoAssociatedObjectFoundException.class)
	public ResponseEntity<Object> handleNoAssociatedObjectFoundException(NoAssociatedObjectFoundException ex)
	{
		return structure(HttpStatus.NOT_FOUND,ex.getMessage(),"user is not associated");
	}
	@ExceptionHandler(UserRoleIsNotExistedException.class)
	public ResponseEntity<Object> handleUserRoleIsNotExistedException(UserRoleIsNotExistedException ex)
	{
		return structure(HttpStatus.NOT_FOUND, ex.getMessage(), "invalid userrole");
	}
	@ExceptionHandler(CurrentClassHourEmptyException.class)
	public ResponseEntity<Object> handleCurrentClassHourEmptyException( CurrentClassHourEmptyException ex)
	{
		return structure(HttpStatus.NOT_FOUND, ex.getMessage(), "current class hour is not existe") ;
	}
	@ExceptionHandler(ClassHourAlreadyGeneratedException.class)
	public ResponseEntity<Object> handleClassHourAlreadyGeneratedException(ClassHourAlreadyGeneratedException ex)
	{
		return structure(HttpStatus.BAD_REQUEST,ex.getMessage(), "class hours already generated");
	}

	@ExceptionHandler(InvalidOpenTimeAndCloseTimeException.class)
	public ResponseEntity<Object> handleInvalidOpenTimeAndCloseTime(InvalidOpenTimeAndCloseTimeException ex)
	{
		return structure(HttpStatus.BAD_REQUEST, ex.getMessage(), "invalid open time or close time");
	}

	@ExceptionHandler(InvalidBreakTimeException.class)
	public ResponseEntity<Object> handleInvalidBreakTimeException(InvalidBreakTimeException ex)
	{
		return structure(HttpStatus.BAD_REQUEST,ex.getMessage(),"invalid breaktime");
	}
	@ExceptionHandler(InvalidLunchTimeException.class)
	public ResponseEntity<Object> handleInvalidLunchTimeException(InvalidLunchTimeException ex)
	{
		return structure(HttpStatus.BAD_REQUEST,ex.getMessage(),"invalid lunchtime");
	}
	@ExceptionHandler(InvalidClassHourEndException.class)
	public ResponseEntity<Object> handleInvalidClassHourEndException(InvalidClassHourEndException ex)
	{
		return structure(HttpStatus.BAD_REQUEST,ex.getMessage(),"invalid classhour ending");
	}
	@ExceptionHandler(RoomNumberAlreadyExistedException.class)
	public ResponseEntity<Object> handleRoomNumberAlreadyExistedException(RoomNumberAlreadyExistedException ex)
	{
		return structure(HttpStatus.BAD_REQUEST,ex.getMessage(), "room number already existed");
	}


}
