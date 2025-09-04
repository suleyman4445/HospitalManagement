package com.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
	
	private String username;
	private String name;
	private String email;
	private String password;
	
	private int age;
	private String gender;
	private String address;
	private String phone;
}
