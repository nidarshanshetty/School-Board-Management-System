package com.school.sbm.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sbm.entity.AcademicProgram;
import com.school.sbm.entity.User;
import com.school.sbm.enums.UserRole;
import com.school.sbm.exception.AcademicProgamNotFoundException;
import com.school.sbm.exception.AdminAlreadyExistExceptoon;
import com.school.sbm.exception.AdmineCannotBeAssignedToAcademicProgram;
import com.school.sbm.exception.UserObjectNotFoundException;
import com.school.sbm.repository.IAcademicProgramRepository;
import com.school.sbm.repository.IUserRepository;
import com.school.sbm.reqeustdto.UserRequest;
import com.school.sbm.responsedto.UserResponse;
import com.school.sbm.service.IUserService;
import com.school.sbm.utility.ResponseStructure;

@Service
public class UserServiceIMPL implements IUserService
{
	@Autowired
	private IAcademicProgramRepository IAcademicProgramRepository;

	@Autowired
	private IUserRepository iuserRepository;

	@Autowired
	private ResponseStructure<UserResponse>responseStructure;


	private User mapToUserRequest(UserRequest userRequest)
	{
		return User.builder()
				.username(userRequest.getUsername())
				.password(userRequest.getPassword())
				.firstName(userRequest.getFirstName())
				.lastName(userRequest.getLastName())
				.contactNo(userRequest.getContactNo())
				.email(userRequest.getEmail())
				.userRole(userRequest.getUserRole())
				.build();	
	}
	private UserResponse mapToUserResponse(User user)
	{
		return UserResponse.builder()
				.userId(user.getUserId())
				.username(user.getUsername())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.contactNo(user.getContactNo())
				.email(user.getEmail())
				.userRole(user.getUserRole())
				.build();
	}


	@Override
	public  ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest userRequest) 
	{
		if(userRequest.getUserRole().equals(UserRole.ADMIN))
		{
			if(iuserRepository.existsByUserRole(userRequest.getUserRole())==false)
			{
				User user = iuserRepository.save(mapToUserRequest(userRequest));

				responseStructure.setStatus(HttpStatus.CREATED.value());
				responseStructure.setMessage("admine saved successfully");
				responseStructure.setData(mapToUserResponse(user));

				return new  ResponseEntity<ResponseStructure<UserResponse>>(responseStructure,HttpStatus.CREATED);
			}
			else
			{
				throw new AdminAlreadyExistExceptoon("admin already exist");
			}
		}
		else
		{

			User user = iuserRepository.save(mapToUserRequest(userRequest));
			responseStructure.setStatus(HttpStatus.CREATED.value());
			responseStructure.setMessage("user saved successfully");
			responseStructure.setData(mapToUserResponse(user));

			return new  ResponseEntity<ResponseStructure<UserResponse>>(responseStructure,HttpStatus.CREATED);

		}
	}
	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> findUser(Integer userId) 
	{

		User user = iuserRepository.findById(userId)
				.orElseThrow(()->new UserObjectNotFoundException("user not found"));

		responseStructure.setStatus(HttpStatus.FOUND.value());
		responseStructure.setMessage("user found successfully");
		responseStructure.setData(mapToUserResponse(user));


		return new  ResponseEntity<ResponseStructure<UserResponse>>(responseStructure,HttpStatus.FOUND);
	}
	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(Integer userId)
	{
		User user = iuserRepository.findById(userId)
				.orElseThrow(()->new UserObjectNotFoundException("user not found"));

		if(user.isDeleted()==true)
		{
			throw new UserObjectNotFoundException("user not found");
		}

		user.setDeleted(true);
		User save = iuserRepository.save(user);


		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMessage("user deleted successfully");
		responseStructure.setData(mapToUserResponse(save));


		return new  ResponseEntity<ResponseStructure<UserResponse>>(responseStructure,HttpStatus.OK);
	}
	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> assignUser(int userId, int programId) 
	{
		User user = iuserRepository.findById(userId)
				.orElseThrow(()-> new UserObjectNotFoundException("user not found"));

		AcademicProgram academicProgram = IAcademicProgramRepository.findById(programId)
				.orElseThrow(()->new AcademicProgamNotFoundException("AcademicProgam not found"));

		if(user.getUserRole().equals(UserRole.ADMIN))
		{
			throw new AdmineCannotBeAssignedToAcademicProgram("admine cannot assign");
		}
		else
		{
			user.getAcademicProgram().add(academicProgram);
			iuserRepository.save(user);
			academicProgram.getUsers().add(user);
			IAcademicProgramRepository.save(academicProgram );

			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("updated successfully");
			responseStructure.setData(mapToUserResponse(user));


			return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure,HttpStatus.OK);
		}
	}

}


