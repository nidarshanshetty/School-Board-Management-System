package com.school.sbm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sbm.reqeustdto.UserRequest;
import com.school.sbm.responsedto.UserResponse;
import com.school.sbm.service.IUserService;
import com.school.sbm.utility.ResponseStructure;

@RestController
public class UserController 
{

	@Autowired
	private IUserService  iUserService ;

	@PostMapping("/users")
	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(@RequestBody  UserRequest userRequest)
	{
		System.out.println("controller");
		return iUserService.saveUser(userRequest);
	}
	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> findUser(@PathVariable Integer userId)
	{
		return iUserService.findUser(userId);
	}
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>>deleteUser(@PathVariable Integer userId)
	{
		return iUserService.deleteUser(userId);
	}

}
