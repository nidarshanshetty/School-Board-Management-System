package com.school.sbm.exception;

import lombok.Getter;

@Getter
public class SubjectNotFoundException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public SubjectNotFoundException(String message)
	{
		this.message=message;
	}

}
