package com.school.sbm.reqeustdto;

import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequest
{

	private LocalTime opensAt;
	private LocalTime closesAt;
	private int classHoursPerDay;
	private int classHourLengthInMinute;
	private LocalTime breakTime;
	private int breakLengthInMinute;
	private LocalTime lunchTime;
	private int lunchLengthInMinute;

}
