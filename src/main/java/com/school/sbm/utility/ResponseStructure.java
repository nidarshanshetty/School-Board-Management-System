package com.school.sbm.utility;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;


@Component
@Setter
@Getter

public class ResponseStructure<T>
{
	private Integer status;
	private String message;
	private T data;
}
