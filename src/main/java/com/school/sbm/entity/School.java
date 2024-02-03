package com.school.sbm.entity;

import java.util.List;

import jakarta.persistence.Column;
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
	@Column(unique = true)
	private String schoolName;
	@Column(unique = true)
	private Long contactNo;
	@Column(unique = true)
	private String emailId;
	@Column(unique = true)
	private String address;
	private boolean isDeleted;

	@OneToOne
	private Schedule schedule;

	@OneToMany(mappedBy = "school")
	private List<AcademicProgram>aList;


}
