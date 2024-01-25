package com.school.sbm.exception;

import lombok.Getter;

@Getter
public class StudentCannotBeAssignedToAcademicProgram extends RuntimeException 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public StudentCannotBeAssignedToAcademicProgram(String message)
	{
		this.message=message;
	}
}
