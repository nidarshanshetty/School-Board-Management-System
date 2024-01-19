package com.school.sbm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.sbm.entity.Subject;

@Repository
public interface ISubjectRepository extends JpaRepository<Subject, Integer>
{
	Optional<Subject>findBySubjectNames(String name);

}
