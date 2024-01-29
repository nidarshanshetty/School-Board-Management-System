package com.school.sbm.reqeustdto;

import java.time.LocalDate;

import com.school.sbm.enums.ProgramType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcademicProgramRequest 
{
	private ProgramType programType;
	private String programName;
	private LocalDate beginsAt;
	private LocalDate endsAt;
}
