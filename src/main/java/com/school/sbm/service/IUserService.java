package com.school.sbm.service;

import org.springframework.http.ResponseEntity;

import com.school.sbm.reqeustdto.UserRequest;
import com.school.sbm.responsedto.UserResponse;
import com.school.sbm.utility.ResponseStructure;


public interface IUserService 
{


	ResponseEntity<ResponseStructure<UserResponse>> findUser(Integer userId);

	ResponseEntity<ResponseStructure<UserResponse>> deleteUser(Integer userId);

	ResponseEntity<ResponseStructure<UserResponse>> assignUser(int userId, int programId);

	ResponseEntity<ResponseStructure<UserResponse>> addSubjectToTheTeacher(int subjectId, int userId);

	ResponseEntity<ResponseStructure<UserResponse>> registerAdmin(UserRequest userRequest);

	ResponseEntity<ResponseStructure<UserResponse>> addOtherUsers(UserRequest userRequest);


}
