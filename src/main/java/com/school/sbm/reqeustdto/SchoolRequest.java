package com.school.sbm.reqeustdto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolRequest 
{
	@NotBlank(message = "School Name is required")
	private String schoolName;
	@Min(value = 0000020000l, message = " phone number must be valid")
	@Max(value = 9999999999l, message = " phone number must be valid")
	@Column(unique = true)
	private Long contactNo;
	@NotBlank(message = "Email is required")
	@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email ")
	@Column(unique = true)
	private String emailId;
	@NotBlank(message = "School Address is required")
	private String address;
}
