package com.school.sbm.exception;

import lombok.Getter;

@Getter
public class AdminNotFoundException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public AdminNotFoundException(String message)
	{
		this.message=message;
	}
}
