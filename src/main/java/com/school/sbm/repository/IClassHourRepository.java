package com.school.sbm.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.sbm.entity.AcademicProgram;
import com.school.sbm.entity.ClassHour;
import com.school.sbm.entity.User;
@Repository
public interface IClassHourRepository extends JpaRepository<ClassHour, Integer>
{
	boolean existsByBeginsAtBetweenAndRoomNo(LocalDateTime beginsAt, LocalDateTime endsAt, int roomNo);

	List<ClassHour> findByUser(User user);

	List<ClassHour> findByAcademicProgramsAndBeginsAtAfterAndBeginsAtBefore(AcademicProgram academicPrograms,
			LocalDateTime truncatedTo, LocalDateTime truncatedTo2);

	List<ClassHour> findAllByAcademicProgramsAndBeginsAtBetween(AcademicProgram academicProgram, LocalDateTime fromTime,
			LocalDateTime toTime);
}
