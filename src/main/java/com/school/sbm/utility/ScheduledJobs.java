package com.school.sbm.utility;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.school.sbm.entity.AcademicProgram;
import com.school.sbm.repository.IAcademicProgramRepository;
import com.school.sbm.serviceimpl.AcademicProgramServiceImpl;
import com.school.sbm.serviceimpl.ClassHourServiceImpl;
import com.school.sbm.serviceimpl.SchoolServiceImpl;
import com.school.sbm.serviceimpl.UserServiceImpl;

@Component
public class ScheduledJobs 
{
	@Autowired
	private ClassHourServiceImpl classHourServiceImpl;

	@Autowired
	private IAcademicProgramRepository academicProgramRepository;

	@Autowired
	SchoolServiceImpl schoolServiceImpl;

	@Autowired
	UserServiceImpl userServiceImpl;

	@Autowired
	AcademicProgramServiceImpl academicProgramServiceImpl;

	@Scheduled(fixedDelay = 1000l)
	public void test()
	{
		//		userServiceImpl.deleteSoftDeletedData();
		//		academicProgramServiceImpl.deleteSoftDeletedData();
		//		schoolServiceImpl.deleteSoftDeletedData();

	}

	@Scheduled(cron = "0 0 0 * MON ")
	public void classHourWeekly()
	{
		List<AcademicProgram> academicPrograms = academicProgramRepository.findAll();
		for(AcademicProgram academicProgram:academicPrograms)
		{
			//			classHourServiceImpl.autoRepeatWeeklyTimeTable(academicProgram);
		}
	}


}
