package com.demo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Patients")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
	
	@Id
	private Long id;
	@OneToOne
	@MapsId
	@JoinColumn(name="id")
	private User user;
	
	@OneToMany(mappedBy="patient")
	List<Appointment> appointments=new ArrayList<>();
	
	private String name;
	private int age;
	private String gender;
	private String phone;
//	private String email;
	private String address;
}
