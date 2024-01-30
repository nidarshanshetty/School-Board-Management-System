package com.school.sbm.exception;

import lombok.Getter;

@Getter
public class UserRoleIsNotExistedException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public UserRoleIsNotExistedException(String message)
	{
		this.message=message;
	}
}
