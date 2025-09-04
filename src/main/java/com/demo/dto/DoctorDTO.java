package com.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {
	
	private String username;
	private String email;
	private String password;
	private String name;
	private String dept;
	private String phone;
}
