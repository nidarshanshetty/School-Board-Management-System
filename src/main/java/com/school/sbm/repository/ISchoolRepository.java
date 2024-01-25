package com.school.sbm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.sbm.entity.School;

@Repository
public interface ISchoolRepository extends JpaRepository<School, Integer>
{



}
