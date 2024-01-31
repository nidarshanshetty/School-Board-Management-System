package com.school.sbm.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.school.sbm.serviceimpl.AcademicProgramServiceImpl;
import com.school.sbm.serviceimpl.SchoolServiceImpl;
import com.school.sbm.serviceimpl.UserServiceImpl;

@Component
public class ScheduledJobs 
{
	@Autowired
	SchoolServiceImpl schoolServiceImpl;

	@Autowired
	UserServiceImpl userServiceImpl;

	@Autowired
	AcademicProgramServiceImpl academicProgramServiceImpl;

	@Scheduled(fixedDelay = 1000l)
	public void test()
	{
		userServiceImpl.deleteSoftDeletedData();
		academicProgramServiceImpl.deleteSoftDeletedData();
		schoolServiceImpl.deleteSoftDeletedData();
	}


}
