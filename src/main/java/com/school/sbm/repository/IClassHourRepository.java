package com.school.sbm.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.sbm.entity.ClassHour;
@Repository
public interface IClassHourRepository extends JpaRepository<ClassHour, Integer>
{
	boolean existsByBeginsAtBetweenAndRoomNo(LocalDateTime beginsAt, LocalDateTime endsAt, int roomNo);
}
