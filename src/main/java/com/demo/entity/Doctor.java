package com.demo.entity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Doctors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	@OneToOne
	@MapsId
	@JoinColumn(name = "id")
	private User user;
	
	private String dept;
	@OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DoctorAvailability> availDays = new ArrayList<>();
	@OneToMany(mappedBy="doctor")
	private List<Appointment> appointments;
	private String phone;
//	private LocalTime startTime;
//	private LocalTime endTime;
	private Double consulFee;
}
