package com.school.sbm.responsedto;

import java.time.LocalTime;

import com.school.sbm.enums.ProgramType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AcademicProgramResponse 
{
	private int programId;
	private ProgramType programType;
	private String programName;
	private LocalTime beginsAt;
	private LocalTime endsAt;
}
