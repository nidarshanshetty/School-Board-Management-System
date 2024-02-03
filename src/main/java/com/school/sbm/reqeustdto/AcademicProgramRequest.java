	package com.school.sbm.reqeustdto;

import java.time.LocalDate;

import com.school.sbm.enums.ProgramType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcademicProgramRequest 
{
//	@NotBlank(message ="programType cannot be blank")
//	@NotNull(message ="programType cannot be null")
	private ProgramType programType;
//	@NotBlank(message ="programName cannot be blank")
//	@NotNull(message ="programName cannot be null")
	private String programName;
//	@NotBlank(message ="beginsAt cannot be blank")
//	@NotNull(message ="beginsAt cannot be null")
	private LocalDate beginsAt;
//	@NotBlank(message ="endsAt cannot be blank")
//	@NotNull(message ="endsAt cannot be null")
	private LocalDate endsAt;

}
