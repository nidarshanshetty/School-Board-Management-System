package com.school.sbm.responsedto;

import java.time.LocalDate;
import java.util.List;

import com.school.sbm.entity.Subject;
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
	private LocalDate beginsAt;
	private LocalDate endsAt;
	private List<Subject>subjects;
}
