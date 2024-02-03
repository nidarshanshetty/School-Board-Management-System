package com.school.sbm.reqeustdto;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequest
{
	
//	@NotNull(message = "opensAt should not be null")
//	@NotBlank(message = "opensAt should not be blank")
	private LocalTime opensAt;
//	@NotNull(message = "closesAt should not be null")
//	@NotBlank(message = "closesAt should not be blank")
	private LocalTime closesAt;
//	@NotNull(message = "classHoursPerDay should not be null")
//	@NotBlank(message = "classHoursPerDay should not be blank")
	private int classHoursPerDay;
//	@NotNull(message = "classHourLengthInMinute should not be null")
//	@NotBlank(message = "classHourLengthInMinute should not be blank")
	private int classHourLengthInMinute;
//	@NotNull(message = "breakTime should not be null")
//	@NotBlank(message = "breakTime should not be blank")
	private LocalTime breakTime;
//	@NotNull(message = "breakLengthInMinute should not be null")
//	@NotBlank(message = "breakLengthInMinute should not be blank")
	private int breakLengthInMinute;
//	@NotNull(message = "lunchTime should not be null")
//	@NotBlank(message = "lunchTime should not be blank")
	private LocalTime lunchTime;
//	@NotNull(message = "lunchLengthInMinute should not be null")
//	@NotBlank(message = "lunchLengthInMinute should not be blank")
	private int lunchLengthInMinute;
	
}
