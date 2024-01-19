package com.school.sbm.exception;

import lombok.Getter;

@Getter
public class AdmineCannotBeAssignedToAcademicProgram extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public AdmineCannotBeAssignedToAcademicProgram (String message)
	{
		this.message=message;
	}
}
