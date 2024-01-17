package com.school.sbm.exception;

public class UserObjectNotFoundException  extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public UserObjectNotFoundException(String message)
	{
		this.message=message;
	}

	public String getMessage()
	{
		return message;
	}
}
