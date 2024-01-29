package com.school.sbm.responsedto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import com.school.sbm.enums.ClassStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassHourResponse 
{
	private LocalTime classBeginsAt;
	private LocalTime classEndsAt;
	private DayOfWeek day;
	private LocalDate date;
	private int classRoomNumber;
	private ClassStatus classStatus;
}
