package com.school.sbm.exception;

import lombok.Getter;

@Getter
public class AdminAlreadyExistExceptoon extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public AdminAlreadyExistExceptoon(String message)
	{
		this.message=message;
	}
}
