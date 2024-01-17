package com.school.sbm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.sbm.entity.User;
import com.school.sbm.enums.UserRole;

@Repository
public interface IUserRepository  extends JpaRepository<User, Integer>
{
	Boolean existsByUserRole(UserRole userRole);
}
