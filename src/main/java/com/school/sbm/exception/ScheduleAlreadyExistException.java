package com.school.sbm.exception;

import lombok.Getter;

@Getter
public class ScheduleAlreadyExistException extends  RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	public ScheduleAlreadyExistException(String message)
	{
		this.message=message;
	}
}
