package com.school.sbm.reqeustdto;

import com.school.sbm.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest
{
	@NotBlank(message = "Username is required")
	@Column(unique = true)
	@Pattern(regexp = "^[a-zA-Z]+$")
	private String  username;
	@NotEmpty(message ="password cannot be null")
	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must"
			+ " contain at least one letter, one number, one special character")
	private String password;
	@NotNull(message ="First name cannot be null")
	@NotBlank(message= "First name cannot be blank")
	private String firstName;
	@NotEmpty(message ="last name cannot be null")
	private String lastName;
	@Min(value = 6000000000l, message = " phone number must be valid")
	@Max(value = 9999999999l, message = " phone number must be valid")
	@Column(unique = true)
	private long contactNo;
	@NotEmpty(message = "email cannot be blank ")
	@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email ")
	@Column(unique = true)
	private String email;
	@NotBlank(message = "UserRole is required")
	private UserRole userRole;
}
