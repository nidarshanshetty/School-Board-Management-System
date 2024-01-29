package com.school.sbm.responsedto;

import java.time.DayOfWeek;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SchoolResponse 
{
	private Integer schoolId;
	private String schoolName;
	private Long contactNo;
	private String emailId;
	private String address;
	private DayOfWeek weekOffDay;
}
