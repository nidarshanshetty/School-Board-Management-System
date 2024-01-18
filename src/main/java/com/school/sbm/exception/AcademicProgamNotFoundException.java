package com.school.sbm.exception;

import lombok.Getter;

@Getter
public class AcademicProgamNotFoundException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public AcademicProgamNotFoundException(String message)
	{
		this.message=message;
	}
}
