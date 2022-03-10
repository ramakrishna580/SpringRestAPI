package com.spring.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateDTO {

    @Size(min = 3, max = 20)
	private String firstName;

    @Size(min = 3, max = 20)
	private String lastName;

	@Email(message = "Email should be valid email format")
	private String email;

    @Size(min = 3, max = 20)
	private String location;
}
