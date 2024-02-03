package com.school.sbm.reqeustdto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExcelRequest 
{
	private LocalDate fromDate;
	private LocalDate toDate;
	private String filepath;
}
