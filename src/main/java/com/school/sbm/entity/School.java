package com.school.sbm.entity;

import java.time.DayOfWeek;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class School 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer schoolId;
	private String schoolName;
	private Long contactNo;
	private String emailId;
	private String address;
	private DayOfWeek weekOffDay;


	@OneToOne
	private Schedule schedule;

	@OneToMany(mappedBy = "school")
	private List<AcademicProgram>aList;


}
