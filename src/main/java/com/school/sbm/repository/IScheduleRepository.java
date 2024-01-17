package com.school.sbm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.sbm.entity.Schedule;

@Repository
public interface IScheduleRepository  extends JpaRepository<Schedule, Integer>
{

}
