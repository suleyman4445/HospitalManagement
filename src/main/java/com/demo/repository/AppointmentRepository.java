package com.demo.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.entity.Appointment;
import com.demo.entity.Patient;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long>{
	
	public List<Appointment> findByDoctorId(Long doctorId);
	public List<Appointment> findByPatientId(Long patientId);
	public List<Appointment> findByDoctor_IdAndAppointmentDate(Long doctorId, LocalDate date);
//	public Optional<Appointment> findFirstByPatientAndAppointmentDateAfterOrderByAppointmentDateAsc(Patient patient, LocalDateTime now);
	
	@Query(""" 
			SELECT a FROM Appointment a
			WHERE 
				a.patient.id = :patientId AND
				(
					(a.appointmentDate > :today)
					OR (a.appointmentDate = :today AND a.appointmentTime > :nowTime)
				)
			ORDER BY a.appointmentDate ASC, a.appointmentTime ASC
			""")
	List<Appointment> findNextAppointmentForPatient(
			@Param("patientId") Long patientId,
			@Param("today") LocalDate today,
			@Param("nowTime") LocalTime nowTime
			);
	
	
	@Query("""
			SELECT a FROM Appointment a
			WHERE 
				a.doctor.id = :doctorId AND
				(
					(a.appointmentDate > :today)
					OR (a.appointmentDate = :today AND a.appointmentTime > :nowTime)
				)
			ORDER BY a.appointmentDate ASC, a.appointmentTime ASC
			""")
	List<Appointment> findNextAppointmentForDoctor(
			@Param("doctorId") Long doctorId,
			@Param("today") LocalDate today,
			@Param("nowTime") LocalTime nowTime
			);
}
