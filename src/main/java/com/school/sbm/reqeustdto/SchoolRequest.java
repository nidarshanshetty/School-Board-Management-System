package com.school.sbm.reqeustdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolRequest 
{
	@NotBlank(message = "schoolName cannot be blank")
	@NotNull(message = "schoolName cannot be null")
	private String schoolName;
//	@NotBlank(message = "contactNo cannot be blank")
	@NotNull(message = "contactNo cannot be null")
	private Long contactNo;
	@NotBlank(message = "emailId cannot be blank")
	@NotNull(message = "emailId cannot be null")
	@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email ")
	private String emailId;
	@NotBlank(message = "address cannot be blank")
	@NotNull(message = "address cannot be null")
	private String address;
}
