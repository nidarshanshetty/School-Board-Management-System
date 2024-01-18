package com.school.sbm.entity;

import java.time.LocalTime;

import com.school.sbm.enums.ProgramType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcademicProgram 
{
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private int programId;
	private ProgramType programType;
	private String programName;
	private LocalTime beginsAt;
	private LocalTime endsAt;

	@ManyToOne
	private School school;


}
