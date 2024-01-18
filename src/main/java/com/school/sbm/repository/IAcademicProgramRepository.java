package com.school.sbm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.sbm.entity.AcademicProgram;

@Repository
public interface IAcademicProgramRepository extends JpaRepository<AcademicProgram, Integer>
{

}
