package com.school.sbm.exception;

import lombok.Getter;

@Getter
public class SchoolAlreadyExistException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	public SchoolAlreadyExistException(String message)
	{
		this .message= message;
	}


}
