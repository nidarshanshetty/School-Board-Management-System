package com.school.sbm.responsedto;

import com.school.sbm.enums.UserRole;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserResponse 
{
	private Integer userId;
	private String  username;
	private String firstName;
	private String lastName;
	private long contactNo;
	private String email;
	private UserRole userRole;
}
