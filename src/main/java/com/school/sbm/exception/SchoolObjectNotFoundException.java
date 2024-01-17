package com.school.sbm.exception;

public class SchoolObjectNotFoundException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	public SchoolObjectNotFoundException(String message)
	{
		this.message=message;
	}
	public String getMessage()
	{
		return message;
	}
}
