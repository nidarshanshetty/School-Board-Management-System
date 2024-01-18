package com.school.sbm.exception;

import lombok.Getter;

@Getter
public class ScheduleObjectNotFoundException  extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public ScheduleObjectNotFoundException(String message)
	{
		this.message=message;
	}
}
