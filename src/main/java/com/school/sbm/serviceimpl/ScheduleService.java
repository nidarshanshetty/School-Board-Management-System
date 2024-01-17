package com.school.sbm.serviceimpl;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sbm.entity.School;
import com.school.sbm.entity.Schedule;
import com.school.sbm.exception.SchoolObjectNotFoundException;
import com.school.sbm.exception.ScheduleAlreadyExistException;
import com.school.sbm.repository.ISchoolRepository;
import com.school.sbm.repository.IScheduleRepository;
import com.school.sbm.reqeustdto.ScheduleRequest;
import com.school.sbm.responsedto.ScheduleResponse;
import com.school.sbm.service.IScheduleService;
import com.school.sbm.utility.ResponseStructure;

@Service
public class ScheduleService implements IScheduleService
{

	@Autowired
	private ResponseStructure<ScheduleResponse> responseStructure;

	@Autowired
	private IScheduleRepository iSheduleRepository;

	@Autowired
	private ISchoolRepository iSchoolRepository;

	private Schedule mapToScheduleRequest(ScheduleRequest scheduleRequest)
	{

		System.out.println("Hello");
		return Schedule.builder()
				.opensAt(scheduleRequest.getOpensAt())
				.closesAt(scheduleRequest.getClosesAt())
				.classHoursPerDay(scheduleRequest.getClassHoursPerDay())
				.classHourLengthInMinute(Duration.ofMinutes(scheduleRequest.getClassHourLengthInMinute()))
				.breakTime(scheduleRequest.getBreakTime())
				.breakLengthInMinute(Duration.ofMinutes(scheduleRequest.getBreakLengthInMinute()))
				.lunchTime(scheduleRequest.getLunchTime())
				.lunchLengthInMinute(Duration.ofMinutes(scheduleRequest.getLunchLengthInMinute()))
				.build();
	}

	private ScheduleResponse mapToScheduleResponse(Schedule schedule)
	{
		return ScheduleResponse.builder()
				.scheduleId(schedule.getScheduleId())
				.opensAt(schedule.getOpensAt())
				.closesAt(schedule.getClosesAt())
				.classHoursPerDay(schedule.getClassHoursPerDay())
				.classHourLengthInMinute((int)(Duration.ofMinutes(schedule.getClassHourLengthInMinute().toMinutes()).toMinutes()))
				.breakTime(schedule.getBreakTime())
				.breakLengthInMinute((int)(Duration.ofMinutes(schedule.getBreakLengthInMinute().toMinutes()).toMinutes()))
				.lunchTime(schedule.getLunchTime())
				.lunchLengthInMinute((int)(Duration.ofMinutes(schedule.getLunchLengthInMinute().toMinutes()).toMinutes()))
				.build();
	}


	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponse>> saveSchedule(int schoolId,
			ScheduleRequest sheduleRequest) 
	{
		School school = iSchoolRepository.findById(schoolId)
				.orElseThrow(()-> new SchoolObjectNotFoundException("school not found"));

		if(school.getSchedule()==null)
		{

			System.out.println(sheduleRequest.getOpensAt());
			Schedule shedule = iSheduleRepository.save(mapToScheduleRequest(sheduleRequest));
			school.setSchedule(shedule);
			iSchoolRepository.save(school);

			responseStructure.setStatus(HttpStatus.CREATED.value());
			responseStructure.setMessage("shedule saved successfully");
			responseStructure.setData(mapToScheduleResponse(shedule));


			return new ResponseEntity<ResponseStructure<ScheduleResponse>>(responseStructure,HttpStatus.CREATED);

		}
		else
		{
			throw new ScheduleAlreadyExistException("shedule already existed");
		}

	}

}
