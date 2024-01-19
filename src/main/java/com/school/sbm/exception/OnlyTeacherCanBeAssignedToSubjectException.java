package com.school.sbm.exception;

import lombok.Getter;

@Getter
public class OnlyTeacherCanBeAssignedToSubjectException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public OnlyTeacherCanBeAssignedToSubjectException(String message)
	{
		this.message=message;
	}
}
