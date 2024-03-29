package com.school.sbm.serviceimpl;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.school.sbm.entity.AcademicProgram;
import com.school.sbm.entity.ClassHour;
import com.school.sbm.entity.Schedule;
import com.school.sbm.entity.School;
import com.school.sbm.entity.Subject;
import com.school.sbm.entity.User;
import com.school.sbm.enums.ClassStatus;
import com.school.sbm.enums.UserRole;
import com.school.sbm.exception.AcademicProgamNotFoundException;
import com.school.sbm.exception.ClassHourAlreadyGeneratedException;
import com.school.sbm.exception.CurrentClassHourEmptyException;
import com.school.sbm.exception.OnlyTeacherCanBeAssignedToSubjectException;
import com.school.sbm.exception.RoomNumberAlreadyExistedException;
import com.school.sbm.exception.ScheduleObjectNotFoundException;
import com.school.sbm.exception.SubjectNotFoundException;
import com.school.sbm.exception.UserObjectNotFoundException;
import com.school.sbm.repository.IAcademicProgramRepository;
import com.school.sbm.repository.IClassHourRepository;
import com.school.sbm.repository.ISubjectRepository;
import com.school.sbm.repository.IUserRepository;
import com.school.sbm.reqeustdto.ClassHourRequest;
import com.school.sbm.reqeustdto.ExcelRequest;
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

	private boolean isBreakTime(LocalDateTime currentTime, LocalTime breakStartTime, LocalTime breakEndTime) {
		return  currentTime.toLocalTime().equals(breakStartTime) ||
				(currentTime.toLocalTime().isAfter(breakStartTime) && currentTime.toLocalTime().isBefore(breakEndTime));

	}

	private boolean isLunchTime(LocalDateTime currentTime, LocalTime lunchStartTime, LocalTime lunchEndTime) {
		return currentTime.toLocalTime().equals(lunchStartTime) ||
				(currentTime.toLocalTime().isAfter(lunchStartTime) && currentTime.toLocalTime().isBefore(lunchEndTime));

	}

	public List<ClassHour> generateClassHour(AcademicProgram academicProgram) {

		List<ClassHour> listOfClassHour = new ArrayList<ClassHour>();

		School school = academicProgram.getSchool();
		Schedule schedule = school.getSchedule();

		if (schedule != null) {


			if(academicProgram.getListOfClassHours().isEmpty())
			{

				LocalDate programBeginsAt = academicProgram.getBeginsAt();

				LocalDateTime currentDateTime;
				if(programBeginsAt.isAfter(LocalDate.now()))
					currentDateTime = programBeginsAt.atTime(schedule.getOpensAt());
				else 
					currentDateTime = LocalDateTime.now().with(schedule.getOpensAt());

				LocalDateTime lastWorkingDay;
				if(!currentDateTime.equals(currentDateTime.with(DayOfWeek.MONDAY)))
					lastWorkingDay = LocalDateTime.now().plusWeeks(1).with(DayOfWeek.SATURDAY);
				else
					lastWorkingDay = LocalDateTime.now().with(DayOfWeek.SATURDAY);

				LocalTime breakTimeStart = schedule.getBreakTime();
				LocalTime breakTimeEnd = schedule.getBreakTime().plusMinutes(schedule.getBreakLengthInMinute().toMinutes());
				LocalTime lunchTimeStart = schedule.getLunchTime();
				LocalTime lunchTimeEnd = schedule.getLunchTime().plusMinutes(schedule.getLunchLengthInMinute().toMinutes());

				while(currentDateTime.isBefore(lastWorkingDay.plusDays(1))) {

					if(!currentDateTime.equals(currentDateTime.with(DayOfWeek.SUNDAY))) {

						for (int hour = 0; hour < schedule.getClassHoursPerDay() + 2; hour++) {

							ClassHour classHour = new ClassHour();

							if (isLunchTime(currentDateTime, lunchTimeStart, lunchTimeEnd)) {

								classHour.setBeginsAt(currentDateTime);
								classHour.setEndsAt(LocalDateTime.now().with(lunchTimeEnd));
								classHour.setClassStatus(ClassStatus.LUNCH_TIME);
								currentDateTime = currentDateTime.plusMinutes(schedule.getLunchLengthInMinute().toMinutes());

							}
							else {
								if (isBreakTime(currentDateTime, breakTimeStart, breakTimeEnd)) {

									classHour.setBeginsAt(currentDateTime);
									classHour.setEndsAt(LocalDateTime.now().with(breakTimeEnd));

									classHour.setClassStatus(ClassStatus.BREAK_TIME);
									currentDateTime = currentDateTime.plusMinutes(schedule.getBreakLengthInMinute().toMinutes());

								} else {

									LocalDateTime beginsAt = currentDateTime;
									LocalDateTime endsAt = beginsAt.plusMinutes(schedule.getClassHourLengthInMinute().toMinutes());

									classHour.setBeginsAt(beginsAt);
									classHour.setEndsAt(endsAt);
									classHour.setClassStatus(ClassStatus.NOT_SCHEDULED);

									currentDateTime = endsAt;
								}
							}
							classHour.setAcademicPrograms(academicProgram);
							listOfClassHour.add(classHour);
						}
						currentDateTime = currentDateTime.plusDays(1).with(schedule.getOpensAt());
					}
					else
						currentDateTime = currentDateTime.plusDays(1).with(schedule.getOpensAt());;
				}
			} 
			else
				throw new ClassHourAlreadyGeneratedException("class hour already generated");
		}
		else
			throw new ScheduleObjectNotFoundException("schedule not found");

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

						throw new RoomNumberAlreadyExistedException("room num existed");
					}
				}
				else
				{

					throw new SubjectNotFoundException("subject not found");
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


	@Override
	public ResponseEntity<ResponseStructure<List<ClassHourResponse>>> autoRepeatWeeklyTimeTable(int programId) 
	{
		AcademicProgram academicProgram = academicProgramRepository.findById(programId)
				.orElseThrow(()-> new AcademicProgamNotFoundException("academic program not found"));

		List<ClassHour> weeklyTimeTable = autoRepeatWeeklyTimeTable(academicProgram);

		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("upcoming weeks class hour generated successfully ");
		responseStructure.setData(mapToClassHourResponse(weeklyTimeTable));

		return new ResponseEntity<ResponseStructure<List<ClassHourResponse>>>(responseStructure,HttpStatus.CREATED);
	} 


	public List<ClassHour> autoRepeatWeeklyTimeTable(AcademicProgram academicProgram)
	{
		List<ClassHour> classHoursForCurrentWeek = classHourRepository.findByAcademicProgramsAndBeginsAtAfterAndBeginsAtBefore(
				academicProgram, LocalDateTime.now().with(DayOfWeek.MONDAY).truncatedTo(ChronoUnit.DAYS),
				LocalDateTime.now().with(DayOfWeek.SUNDAY).truncatedTo(ChronoUnit.DAYS));


		List<ClassHour> duplicatedClassHoursForNextWeek = new ArrayList<>();

		if(classHoursForCurrentWeek.isEmpty())
		{
			throw new CurrentClassHourEmptyException("current class hour is not available");
		}
		for(ClassHour classHour:classHoursForCurrentWeek)
		{

			@SuppressWarnings("static-access")
			ClassHour build = classHour.builder()
			.subject(classHour.getSubject())
			.roomNo(classHour.getRoomNo())
			.academicPrograms(academicProgram)
			.beginsAt(classHour.getBeginsAt().plusWeeks(1))
			.endsAt(classHour.getEndsAt().plusWeeks(1))
			.classStatus(classHour.getClassStatus())
			.user(classHour.getUser())
			.build();

			duplicatedClassHoursForNextWeek.add(build);
		}
		List<ClassHour> saveAll = classHourRepository.saveAll(duplicatedClassHoursForNextWeek);
		return saveAll;
	}

	@SuppressWarnings("resource")
	@Override
	public String writeExcelSheet(int programId, ExcelRequest excelRequest) 
	{

		String folderPath = excelRequest.getFilepath();
		String filePath=folderPath+"\\test.xlsx";


		AcademicProgram academicProgram = academicProgramRepository.findById(programId)
				.orElseThrow(()-> new AcademicProgamNotFoundException("academic program not found "));

		LocalDateTime fromTime = excelRequest.getFromDate().atTime(LocalTime.MIDNIGHT);
		LocalDateTime toTime = excelRequest.getToDate().atTime(LocalTime.MIDNIGHT).plusDays(1);

		List<ClassHour> classHours=classHourRepository.findAllByAcademicProgramsAndBeginsAtBetween(academicProgram,fromTime,toTime);

		XSSFWorkbook workbook=new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();

		int rowNumber =0;
		XSSFRow header = sheet.createRow(rowNumber);
		header.createCell(0).setCellValue("Date");
		header.createCell(1).setCellValue("Begin Time");
		header.createCell(2).setCellValue("End Time");
		header.createCell(3).setCellValue("Subject");
		header.createCell(4).setCellValue("Teacher");
		header.createCell(5).setCellValue("Room No");


		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		for(ClassHour classHour:classHours)
		{			


			String subjectName ="NOT ASSINED";
			String userName="NOT ASSINED";

			if(classHour.getSubject()!=null)
			{
				subjectName=classHour.getSubject().getSubjectNames();
			}
			if(classHour.getUser()!=null)
			{
				userName=classHour.getUser().getUsername();
			}

			XSSFRow row = sheet.createRow(++rowNumber);
			row.createCell(0).setCellValue(dateFormatter.format(classHour.getBeginsAt()));
			row.createCell(1).setCellValue(timeFormatter.format(classHour.getBeginsAt()));
			row.createCell(2).setCellValue(timeFormatter.format(classHour.getEndsAt()));
			row.createCell(3).setCellValue(subjectName);
			row.createCell(4).setCellValue(userName);
			row.createCell(5).setCellValue(classHour.getRoomNo());

		}

		try {
			workbook.write(new FileOutputStream(filePath));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 


		return "success";
	}

	@Override
	public ResponseEntity<?> writeToExcel(MultipartFile file, LocalDate fromDate, LocalDate toDate,int programId) throws Exception
	{
		
		String filepath="C:\\Users\\Saraswathi Rai\\OneDrive\\Desktop\\nidarsh_file_handling\\"+file.getOriginalFilename();
		
		
		AcademicProgram academicProgram = academicProgramRepository.findById(programId)
				.orElseThrow(()-> new AcademicProgamNotFoundException("academic program not found "));


		LocalDateTime fromTime=fromDate.atTime(LocalTime.MIDNIGHT);
		LocalDateTime toTime=toDate.atTime(LocalTime.MIDNIGHT).plusDays(1);


		List<ClassHour> classHours=classHourRepository.findAllByAcademicProgramsAndBeginsAtBetween(academicProgram,fromTime,toTime);


		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


		XSSFWorkbook workbook=new XSSFWorkbook(file.getInputStream());
		workbook.forEach(sheet->{
			int rowNumber =0;
			Row header = sheet.createRow(rowNumber);
			header.createCell(0).setCellValue("Date");
			header.createCell(1).setCellValue("Begin Time");
			header.createCell(2).setCellValue("End Time");
			header.createCell(3).setCellValue("Subject");
			header.createCell(4).setCellValue("Teacher");
			header.createCell(5).setCellValue("Room No");

			for(ClassHour classHour:classHours)
			{
				String subjectName ="NOT ASSINED";
				String userName="NOT ASSINED";


				if(classHour.getSubject()!=null)
				{
					subjectName=classHour.getSubject().getSubjectNames();
				}
				if(classHour.getUser()!=null)
				{
					userName=classHour.getUser().getUsername();
				}

				Row row = sheet.createRow(++rowNumber);
				row.createCell(0).setCellValue(dateFormatter.format(classHour.getBeginsAt()));
				row.createCell(1).setCellValue(timeFormatter.format(classHour.getBeginsAt()));
				row.createCell(2).setCellValue(timeFormatter.format(classHour.getEndsAt()));
				row.createCell(3).setCellValue(subjectName);
				row.createCell(4).setCellValue(userName);
				row.createCell(5).setCellValue(classHour.getRoomNo());

			}

		});

		ByteArrayOutputStream arrayOutputStream= new ByteArrayOutputStream();
		
		workbook.write(arrayOutputStream);
		workbook.write(new FileOutputStream(filepath));
		workbook.close();

		byte [] byteData = arrayOutputStream.toByteArray();

		return ResponseEntity.ok()
				.header("Content disposition","attachment; filename="+file.getOriginalFilename())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(byteData);

	} 

}


