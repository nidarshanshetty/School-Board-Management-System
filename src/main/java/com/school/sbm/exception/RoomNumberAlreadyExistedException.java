package com.school.sbm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomNumberAlreadyExistedException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
}