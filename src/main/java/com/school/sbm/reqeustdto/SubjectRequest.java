package com.school.sbm.reqeustdto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectRequest
{
//	@NotNull(message ="subjectNames cannot be null")
//	@NotBlank(message ="subjectNames cannot be blank")
	private List<String> subjectNames;
}
