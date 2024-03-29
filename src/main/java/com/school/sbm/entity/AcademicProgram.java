package com.school.sbm.entity;

import java.time.LocalDate;
import java.util.List;

import com.school.sbm.enums.ProgramType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	private LocalDate beginsAt;
	private LocalDate endsAt;
	private boolean isDeleted;

	@ManyToOne
	private School school;

	@ManyToMany
	private List<Subject>subjects;

	@ManyToMany
	private List<User>users;


	@OneToMany(mappedBy = "academicPrograms")
	private List<ClassHour> listOfClassHours;


}
