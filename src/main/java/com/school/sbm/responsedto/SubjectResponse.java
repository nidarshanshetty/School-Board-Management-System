package com.school.sbm.responsedto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectResponse 
{
	private int subjectId;
	private List<String> subjectNames;
}
