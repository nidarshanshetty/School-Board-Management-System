package com.school.sbm.reqeustdto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcademicProgramRequest 
{
	@NotBlank(message ="programType is required")
	private String programType;
	@NotBlank(message ="programName is required")
	private String programName;
	@NotNull(message ="beginsAt is required")
	private LocalDate beginsAt;
	@NotNull(message ="endsAt is required")
	private LocalDate endsAt;

}
