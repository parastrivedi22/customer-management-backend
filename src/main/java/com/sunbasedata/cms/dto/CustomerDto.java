package com.sunbasedata.cms.dto;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

	private UUID id;
	@Size(min = 2, max = 20, message = "invalid first name")
	private String firstName;
	@Size(min = 2, max = 30, message = "invalid last name")
	private String lastName;
	private String street;
	private String address;
	private String city;
	private String state;
	@Email(message = "invalid email")
	private String email;
	@Size(min= 10, max = 12, message = "invalid phone number")
	private String phone;
}
