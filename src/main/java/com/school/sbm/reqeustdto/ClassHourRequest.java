package com.school.sbm.reqeustdto;

import java.util.List;

import com.school.sbm.entity.ClassHour;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClassHourRequest 
{
//	@NotNull(message = "class hour Id should not be null")
//	@NotBlank(message = "class hour Id should not be blank")
	private int classHourId;
//	@NotNull(message = "userId  should not be null")
//	@NotBlank(message = "userId  should not be blank")
	private int userId;
//	@NotNull(message = "subjectId  should not be null")
//	@NotBlank(message = "subjectId  should not be blank")
	private int subjectId;
//	@NotNull(message = "roomNo  should not be null")
//	@NotBlank(message = "roomNo  should not be blank")
	private int roomNo;
	private List<ClassHour>classHourList;
}
