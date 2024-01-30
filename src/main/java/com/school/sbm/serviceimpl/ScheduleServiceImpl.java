package com.school.sbm.serviceimpl;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sbm.entity.School;
import com.school.sbm.entity.Schedule;
import com.school.sbm.exception.SchoolObjectNotFoundException;
import com.school.sbm.exception.ScheduleAlreadyExistException;
import com.school.sbm.exception.ScheduleObjectNotFoundException;
import com.school.sbm.repository.ISchoolRepository;
import com.school.sbm.repository.IScheduleRepository;
import com.school.sbm.reqeustdto.ScheduleRequest;
import com.school.sbm.responsedto.ScheduleResponse;
import com.school.sbm.service.IScheduleService;
import com.school.sbm.utility.ResponseStructure;

@Service
public class ScheduleServiceImpl implements IScheduleService
{

	@Autowired
	private ResponseStructure<ScheduleResponse> responseStructure;

	@Autowired
	private IScheduleRepository iScheduleRepository;

	@Autowired
	private ISchoolRepository iSchoolRepository;

	private Schedule mapToScheduleRequest(ScheduleRequest scheduleRequest)
	{
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
			ScheduleRequest scheduleRequest) 
	{
		School school = iSchoolRepository.findById(schoolId)
				.orElseThrow(()-> new SchoolObjectNotFoundException("school not found"));

		if(school.getSchedule()==null)
		{

			Schedule schedule = iScheduleRepository.save(mapToScheduleRequest(scheduleRequest));
			school.setSchedule(schedule);
			iSchoolRepository.save(school);

			responseStructure.setStatus(HttpStatus.CREATED.value());
			responseStructure.setMessage("schedule saved successfully");
			responseStructure.setData(mapToScheduleResponse(schedule));


			return new ResponseEntity<ResponseStructure<ScheduleResponse>>(responseStructure,HttpStatus.CREATED);

		}
		else
		{
			throw new ScheduleAlreadyExistException("schedule already existed");
		}

	}

	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponse>> findSchedule(int schoolId) 
	{
		School school = iSchoolRepository.findById(schoolId)
				.orElseThrow(()->new SchoolObjectNotFoundException("school not found"));

		Schedule schedule = school.getSchedule();
		int scheduleId = schedule.getScheduleId();

		Optional<Schedule> findById = iScheduleRepository.findById(scheduleId );
		schedule= findById.get();

		responseStructure.setStatus(HttpStatus.FOUND.value());
		responseStructure.setMessage("shedule found successfully");
		responseStructure.setData(mapToScheduleResponse(schedule));



		return new ResponseEntity<ResponseStructure<ScheduleResponse>>(responseStructure,HttpStatus.FOUND);

	}

	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponse>> updateSchedule(int scheduleId,
			ScheduleRequest scheduleRequest) {

		Schedule schedule = iScheduleRepository.findById(scheduleId)
				.map(u->{
					Schedule mapToScheduleRequest = mapToScheduleRequest(scheduleRequest);
					mapToScheduleRequest.setScheduleId(scheduleId);
					return iScheduleRepository.save( mapToScheduleRequest );
				})
				.orElseThrow(()-> new ScheduleObjectNotFoundException("shedule not found") );



		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMessage("shedule updated successfully");
		responseStructure.setData(mapToScheduleResponse(schedule));


		return new ResponseEntity<ResponseStructure<ScheduleResponse>>(responseStructure,HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<ScheduleResponse>>deleteSchedule(Schedule schedule)
	{
		int scheduleId = schedule.getScheduleId();

		Schedule schedule1 = iScheduleRepository.findById(scheduleId)
				.orElseThrow(()-> new ScheduleObjectNotFoundException("schedule not found"));

		iScheduleRepository.delete(schedule1);

		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMessage("schedule successfully deleted");
		responseStructure.setData(mapToScheduleResponse(schedule1));

		return new ResponseEntity<ResponseStructure<ScheduleResponse>>(responseStructure,HttpStatus.OK);
	}
}
