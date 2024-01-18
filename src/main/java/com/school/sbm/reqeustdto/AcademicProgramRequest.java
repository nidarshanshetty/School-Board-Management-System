package com.school.sbm.reqeustdto;

import java.time.LocalTime;

import com.school.sbm.enums.ProgramType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcademicProgramRequest 
{
	private ProgramType programType;
	private String programName;
	private LocalTime beginsAt;
	private LocalTime endsAt;
}
