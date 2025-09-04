package com.demo.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Appointments",uniqueConstraints = @UniqueConstraint(
		columnNames = {"doctorId_FK","appointmentDate","appointmentTime"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate appointmentDate;
	private LocalTime appointmentTime;
	private Integer tokenNumber;
	private int slotDuration;
	
	private AppointmentStatus status;
	
	@ManyToOne
	@JoinColumn(name="doctorId_FK")
	private Doctor doctor;
	
	@ManyToOne
	@JoinColumn(name="patientId_FK")
	private Patient patient;
	
	public enum AppointmentStatus {
		PENDING, CONFIRMED, COMPLETED, CANCELLED,MISSED;
	}
	
	
}
