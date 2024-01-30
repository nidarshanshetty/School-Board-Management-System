package com.school.sbm.serviceimpl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sbm.entity.AcademicProgram;
import com.school.sbm.entity.ClassHour;
import com.school.sbm.entity.Schedule;
import com.school.sbm.entity.School;
import com.school.sbm.entity.Subject;
import com.school.sbm.entity.User;
import com.school.sbm.enums.ClassStatus;
import com.school.sbm.enums.UserRole;
import com.school.sbm.exception.AcademicProgamNotFoundException;
import com.school.sbm.exception.OnlyTeacherCanBeAssignedToSubjectException;
import com.school.sbm.exception.ScheduleObjectNotFoundException;
import com.school.sbm.exception.StudentCannotBeAssignedToAcademicProgram;
import com.school.sbm.exception.SubjectNotFoundException;
import com.school.sbm.exception.UserObjectNotFoundException;
import com.school.sbm.repository.IAcademicProgramRepository;
import com.school.sbm.repository.IClassHourRepository;
import com.school.sbm.repository.ISubjectRepository;
import com.school.sbm.repository.IUserRepository;
import com.school.sbm.reqeustdto.ClassHourRequest;
import com.school.sbm.responsedto.ClassHourResponse;
import com.school.sbm.service.IClassHourService;
import com.school.sbm.utility.ResponseStructure;

@Service
public class ClassHourServiceImpl implements IClassHourService
{
	@Autowired
	ISubjectRepository iSubjectRepository;

	@Autowired
	IUserRepository iUserRepository;

	@Autowired
	ResponseStructure<List<ClassHourResponse>> responseStructure;

	@Autowired
	ResponseStructure<List<ClassHour>> responseStructure2;

	@Autowired
	private IAcademicProgramRepository academicProgramRepository;

	@Autowired
	private IClassHourRepository classHourRepository;


	private List<ClassHourResponse> mapToClassHourResponse(List<ClassHour> savedList) {

		List<ClassHourResponse> listOfClassHourResponses = new ArrayList<>();
		savedList.forEach(classHour -> {
			listOfClassHourResponses
			.add(ClassHourResponse.builder()
					.classBeginsAt(classHour.getBeginsAt().toLocalTime())
					.classEndsAt(classHour.getEndsAt().toLocalTime())
					.classRoomNumber(classHour.getRoomNo())
					.classStatus(classHour.getClassStatus())
					.day(classHour.getBeginsAt().getDayOfWeek())
					.date(classHour.getBeginsAt().toLocalDate())
					.build());
		});

		return listOfClassHourResponses;

	}

	private boolean isBreakTime(LocalDateTime currentTime, Schedule schedule) {
		LocalTime breakTimeStart = schedule.getBreakTime();
		LocalTime breakTimeEnd = breakTimeStart.plusMinutes(schedule.getBreakLengthInMinute().toMinutes());

		return (currentTime.toLocalTime().isAfter(breakTimeStart) && currentTime.toLocalTime().isBefore(breakTimeEnd));

	}

	private boolean isLunchTime(LocalDateTime currentTime, Schedule schedule) {
		LocalTime lunchTimeStart = schedule.getLunchTime();
		LocalTime lunchTimeEnd = lunchTimeStart.plusMinutes(schedule.getLunchLengthInMinute().toMinutes());

		return (currentTime.toLocalTime().isAfter(lunchTimeStart) && currentTime.toLocalTime().isBefore(lunchTimeEnd));

	}
	public List<ClassHour> generateClassHour(AcademicProgram academicProgram) {

		List<ClassHour> listOfClassHour = new ArrayList<ClassHour>();

		School school = academicProgram.getSchool();
		Schedule schedule = school.getSchedule();

		if (schedule != null) {

			int weekOffDay = school.getWeekOffDay().getValue();
			int startingDayOfWeek;

			if(weekOffDay == 7) 
				startingDayOfWeek = 1;
			else
				startingDayOfWeek = weekOffDay+1;

			int classHoursPerDay = schedule.getClassHoursPerDay();
			int classHourLengthInMinutes = (int) schedule.getClassHourLengthInMinute().toMinutes();

			int currentDayNum = LocalDateTime.now().toLocalDate().getDayOfWeek().getValue();

			int diff = currentDayNum - startingDayOfWeek;

			LocalDateTime currentTime = LocalDateTime.now().minusDays(diff).with(schedule.getOpensAt());

			LocalTime breakTimeEnd = schedule.getBreakTime().plusMinutes(schedule.getBreakLengthInMinute().toMinutes());

			LocalTime lunchTimeEnd = schedule.getLunchTime().plusMinutes(schedule.getLunchLengthInMinute().toMinutes());


			for (int day = 1; day <= 6; day++) {

				for (int hour = 0; hour < classHoursPerDay + 2; hour++) {

					ClassHour classHour = new ClassHour();


					if (currentTime.toLocalTime().equals(schedule.getLunchTime()) == false && !isLunchTime(currentTime, schedule)) {

						if (!currentTime.toLocalTime().equals(schedule.getBreakTime()) && !isBreakTime(currentTime, schedule)) {

							LocalDateTime beginsAt = currentTime;
							LocalDateTime endsAt = beginsAt.plusMinutes(classHourLengthInMinutes);

							classHour.setBeginsAt(beginsAt);
							classHour.setEndsAt(endsAt);
							classHour.setClassStatus(ClassStatus.NOT_SCHEDULED);

							currentTime = endsAt;

						} else {

							classHour.setBeginsAt(currentTime);
							classHour.setEndsAt(LocalDateTime.now().with(breakTimeEnd));

							classHour.setClassStatus(ClassStatus.BREAK_TIME);
							currentTime = currentTime.plusMinutes(schedule.getBreakLengthInMinute().toMinutes());
						}

					} else {

						classHour.setBeginsAt(currentTime);
						classHour.setEndsAt(LocalDateTime.now().with(lunchTimeEnd));
						classHour.setClassStatus(ClassStatus.LUNCH_TIME);
						currentTime = currentTime.plusMinutes(schedule.getLunchLengthInMinute().toMinutes());

					}

					classHour.setAcademicPrograms(academicProgram);
					listOfClassHour.add(classHour);
				}

				currentTime = currentTime.plusDays(1).with(schedule.getOpensAt());

			}
		} else {

			throw new ScheduleObjectNotFoundException("schedule not found");

		}

		return listOfClassHour;

	}

	@Override
	public ResponseEntity<ResponseStructure<List<ClassHourResponse>>> generateClassHourForAcademicProgram(int programId) 
	{
		return academicProgramRepository.findById(programId)
				.map(academicProgram -> {

					List<ClassHour> listOfClassHours = generateClassHour(academicProgram);


					List<ClassHour> savedList = classHourRepository.saveAll(listOfClassHours);
					responseStructure.setStatus(HttpStatus.CREATED.value());
					responseStructure.setMessage("class hour generated successfully");
					responseStructure.setData(mapToClassHourResponse(savedList));

					return new ResponseEntity<ResponseStructure<List<ClassHourResponse>>>(responseStructure,HttpStatus.CREATED);

				}).orElseThrow(() -> new AcademicProgamNotFoundException("academic program not found"));
	}



	@Override
	public ResponseEntity<ResponseStructure<List<ClassHourResponse>>> updateClassHour(List<ClassHourRequest> classHourRequests) 
	{
		List<ClassHour>chList = new ArrayList<>();
		for(ClassHourRequest classHourRequest:classHourRequests)
		{

			int classHourId = classHourRequest.getClassHourId();
			int subjectId = classHourRequest.getSubjectId();
			int userId = classHourRequest.getUserId();
			int roomNo = classHourRequest.getRoomNo();

			Subject subject = iSubjectRepository.findById(subjectId)
					.orElseThrow(()-> new SubjectNotFoundException("subject not found"));
			User user = iUserRepository.findById(userId)
					.orElseThrow(()-> new UserObjectNotFoundException("user not found"));
			ClassHour classHour = classHourRepository.findById(classHourId)
					.orElseThrow();

			LocalDateTime beginsAt = classHour.getBeginsAt();
			LocalDateTime endsAt = classHour.getEndsAt();
			LocalDateTime currentDateTime = LocalDateTime.now();
			if(user.getUserRole().equals(UserRole.TEACHER))
			{
				if(user.getSubject().equals(subject))
				{
					if(classHourRepository.existsByBeginsAtBetweenAndRoomNo(beginsAt,endsAt,roomNo)==false)
					{
						if(classHour.getClassStatus().equals(ClassStatus.BREAK_TIME)||classHour.getClassStatus().equals(ClassStatus.LUNCH_TIME))
						{

						}
						else if(currentDateTime.isAfter(beginsAt)&&currentDateTime.isBefore(endsAt))
						{
							classHour.setUser(user);
							classHour.setRoomNo(roomNo);
							classHour.setSubject(subject);
							classHour.setClassStatus(ClassStatus.ONGOING);
							ClassHour save = classHourRepository.save(classHour);

							chList.add(save);
						}
						else if(currentDateTime.isAfter(endsAt))
						{
							classHour.setUser(user);
							classHour.setRoomNo(roomNo);
							classHour.setSubject(subject);
							classHour.setClassStatus(ClassStatus.COMPLETED);
							ClassHour save = classHourRepository.save(classHour);

							chList.add(save);
						}
						else 
						{
							classHour.setUser(user);
							classHour.setRoomNo(roomNo);
							classHour.setSubject(subject);
							classHour.setClassStatus(ClassStatus.UPCOMING);
							ClassHour save = classHourRepository.save(classHour);

							chList.add(save);
						}

					}
					else
					{
						//room no existed
						throw new StudentCannotBeAssignedToAcademicProgram("room num existed");
					}
				}
				else
				{
					//subject not found
					throw new StudentCannotBeAssignedToAcademicProgram("subject not found");
				}
			}
			else
			{
				throw new OnlyTeacherCanBeAssignedToSubjectException("only teacher allowed");
			}
		}

		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("upadeted successfully");
		responseStructure.setData(mapToClassHourResponse(chList));
		return new ResponseEntity<ResponseStructure<List<ClassHourResponse>>>(responseStructure,HttpStatus.CREATED);
	}

	public void deleteClassHour(List<ClassHour> classHours)
	{
		for(ClassHour classHour:classHours)
		{
			int classHourId = classHour.getClassHourId();
			ClassHour hour = classHourRepository.findById(classHourId)
					.orElseThrow(()-> new ScheduleObjectNotFoundException(" not the exception"));

			classHourRepository.delete(hour);
		}
	}

}
