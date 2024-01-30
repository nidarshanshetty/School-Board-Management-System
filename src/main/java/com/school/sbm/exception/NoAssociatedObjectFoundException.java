package com.school.sbm.exception;

import lombok.Getter;

@Getter
public class NoAssociatedObjectFoundException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public NoAssociatedObjectFoundException(String message)
	{
		this.message=message;
	}
}
