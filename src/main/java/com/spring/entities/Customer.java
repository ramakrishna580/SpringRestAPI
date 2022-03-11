package com.spring.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer custId;
	
	@Column(name = "first_name")
	@NotBlank(message = "First Name cannot be null or blank")
    @Size(min = 3, max = 20)
	private String firstName;
	
	@Column(name = "last_name")
	@NotBlank(message = "Last Name cannot be null or blank")
    @Size(min = 3, max = 20)
	private String lastName;

	@NotBlank(message = "Email cannot be null or blank")
	@Email(message = "Email should be valid email format")
	private String email;

	@NotBlank(message = "Location cannot be null or blank")
	private String location;
}
