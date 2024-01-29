package com.school.sbm.reqeustdto;

import java.util.List;

import com.school.sbm.entity.ClassHour;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClassHourRequest 
{
	private int classHourId;
	private int userId;
	private int subjectId;
	private int roomNo;
	private List<ClassHour>classHourList;
}
