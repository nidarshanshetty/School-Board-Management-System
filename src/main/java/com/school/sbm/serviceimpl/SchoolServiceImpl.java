package com.school.sbm.serviceimpl;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.school.sbm.entity.School;
import com.school.sbm.entity.User;
import com.school.sbm.enums.UserRole;
import com.school.sbm.exception.AdminNotFoundException;
import com.school.sbm.exception.SchoolAlreadyExistException;
import com.school.sbm.exception.SchoolObjectNotFoundException;
import com.school.sbm.repository.ISchoolRepository;
import com.school.sbm.repository.IUserRepository;
import com.school.sbm.reqeustdto.SchoolRequest;
import com.school.sbm.responsedto.SchoolResponse;
import com.school.sbm.service.ISchoolService;
import com.school.sbm.utility.ResponseStructure;

@Service
public class SchoolServiceImpl  implements ISchoolService
{
	@Autowired
	private IUserRepository  iUserRepository;

	@Autowired	
	private ISchoolRepository iSchoolRepository;

	@Autowired
	private ResponseStructure<SchoolResponse> responseStructure;

	@Autowired
	private ResponseStructure<List<SchoolResponse>> structure;


	private School mapToSchoolRequest(SchoolRequest schoolRequest)
	{
		return School.builder()
				.schoolName(schoolRequest.getSchoolName())
				.contactNo(schoolRequest.getContactNo())
				.emailId(schoolRequest.getEmailId())
				.address(schoolRequest.getAddress())
				.weekOffDay(DayOfWeek.valueOf(schoolRequest.getWeekOffDay().toUpperCase()))
				.build();
	}

	private SchoolResponse mapToSchoolResponse(School school)
	{
		return SchoolResponse.builder()
				.schoolId(school.getSchoolId())
				.schoolName(school.getSchoolName())
				.contactNo(school.getContactNo())
				.emailId(school.getEmailId())
				.address(school.getAddress())
				.weekOffDay(school.getWeekOffDay())
				.build();
	}

	@Override
	public ResponseEntity<ResponseStructure<SchoolResponse>> saveSchool(Integer userId, SchoolRequest schoolRequest) 
	{
		String userName=SecurityContextHolder.getContext()
				.getAuthentication()
				.getName();

		User user = iUserRepository.findByUsername(userName)
				.orElseThrow(()-> new UsernameNotFoundException("username not found"));

		if(user.getUserRole().equals(UserRole.ADMIN))
		{
			if(user.getSchool()==null)
			{
				School school = iSchoolRepository.save(mapToSchoolRequest(schoolRequest));
				iUserRepository.findAll().forEach(userFromRepo->{
					userFromRepo.setSchool(school);
					iUserRepository.save(user);
				});


				responseStructure.setStatus(HttpStatus.CREATED.value());
				responseStructure.setMessage("School  Created successfully");
				responseStructure.setData(mapToSchoolResponse(school));

				return new  ResponseEntity<ResponseStructure<SchoolResponse>>(responseStructure,HttpStatus.CREATED);
			}
			else
			{

				throw new SchoolAlreadyExistException("school already exist");
			}

		}
		else
		{
			throw new AdminNotFoundException("admin not found");
		}


	}


	@Override
	public ResponseEntity<ResponseStructure<SchoolResponse>> updateSchool(int schoolId, SchoolRequest school) 
	{
		School save = iSchoolRepository.findById(schoolId)
				.map(u->{
					School mapToSchoolRequest = mapToSchoolRequest(school);
					mapToSchoolRequest.setSchoolId(schoolId);
					return iSchoolRepository.save(mapToSchoolRequest);
				})
				.orElseThrow(()-> new SchoolObjectNotFoundException("School Not Found"));


		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMessage("School Updated successfully");
		responseStructure.setData(mapToSchoolResponse(save));

		return new ResponseEntity<ResponseStructure<SchoolResponse>>(responseStructure,HttpStatus.OK);
	}



	@Override
	public ResponseEntity<ResponseStructure<List<SchoolResponse>>> findAllSchool() 
	{
		List<School> findAll = iSchoolRepository.findAll();

		List<SchoolResponse> collect = findAll.stream()
				.map(u-> mapToSchoolResponse(u))
				.collect(Collectors.toList());


		structure.setStatus(HttpStatus.FOUND.value());
		structure.setMessage("School Found successfully");
		structure.setData(collect );

		return new ResponseEntity<ResponseStructure<List<SchoolResponse>>>(structure,HttpStatus.FOUND);
	}

	@Override
	public ResponseEntity<ResponseStructure<SchoolResponse>>deleteSchool(int schoolId) 
	{
		School save = iSchoolRepository.findById(schoolId)
				.orElseThrow(()->new SchoolObjectNotFoundException("School Not Found"));


		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMessage("school deleted successfully");
		responseStructure.setData(mapToSchoolResponse(save));

		return new ResponseEntity<ResponseStructure<SchoolResponse>>(responseStructure,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<SchoolResponse>> findSchoolById(int schoolId) 
	{
		School save = iSchoolRepository.findById(schoolId
				).orElseThrow(()-> new SchoolObjectNotFoundException("School Not Found"));


		responseStructure.setStatus(HttpStatus.FOUND.value());
		responseStructure.setMessage("School found successfully");
		responseStructure.setData(mapToSchoolResponse(save));

		return new ResponseEntity<ResponseStructure<SchoolResponse>>(responseStructure,HttpStatus.FOUND);

	}

}