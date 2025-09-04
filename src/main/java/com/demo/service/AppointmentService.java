package com.demo.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dto.AppointmentDTO;
import com.demo.entity.Appointment;
import com.demo.entity.Doctor;
import com.demo.entity.DoctorAvailability;
import com.demo.entity.Patient;
import com.demo.entity.User;
import com.demo.entity.Appointment.AppointmentStatus;
import com.demo.repository.AppointmentRepository;
import com.demo.repository.DoctorAvailabilityRepository;
import com.demo.repository.DoctorRepository;
import com.demo.repository.PatientRepository;

@Service
public class AppointmentService {
	
	@Autowired
	private DoctorAvailabilityRepository availableRepo;
	@Autowired
	private AppointmentRepository apptRepo;
	@Autowired
	private DoctorService docService;
	@Autowired
	private PatientService patService;
	@Autowired
	private DoctorAvailabilityService availService;
	
	public List<Appointment> getAppointmentsByDoctor(Long doctorId){
		return apptRepo.findByDoctorId(doctorId);
	}
	
	public List<Appointment> getAppointmentsByPatient(Long patientId){
		return apptRepo.findByPatientId(patientId);
	}
	
	public List<LocalTime> getAllPossibleSlots(Long doctorId, LocalDate date) {
	    // 10 AM to 4 PM, every 30 minutes
	    List<LocalTime> slots = new ArrayList<>();
	    LocalTime start = LocalTime.of(10, 0);
	    LocalTime end = LocalTime.of(16, 0);
	    while (!start.isAfter(end)) {
	        slots.add(start);
	        start = start.plusMinutes(30);
	    }
	    return slots;
	}

	public List<LocalTime> getAvailableSlots(Long doctorId, LocalDate date) {
	    List<LocalTime> allSlots = getAllPossibleSlots(doctorId, date);
	    List<LocalTime> bookedSlots = apptRepo.findByDoctor_IdAndAppointmentDate(doctorId, date)
	                                          .stream()
	                                          .map(Appointment::getAppointmentTime)
	                                          .collect(Collectors.toList());

	    return allSlots.stream()
	                   .filter(slot -> !bookedSlots.contains(slot))
	                   .collect(Collectors.toList());
	}

	
	public Appointment confirmAppointment(User user, AppointmentDTO form) {
		Patient patient = patService.findPatientById(user.getId());
//		if(patient == null)
//			return "redirect: /error-page";
		Doctor doctor = docService.getDoctorById(form.getDoctorId());
//		if(doctor == null)
//			return "redirect: /error-page";
		DoctorAvailability availability = availService
				.getDoctorAvailabilityByDay(doctor.getId(), form.getDate().getDayOfWeek());
		
		Appointment appointment = new Appointment();
		appointment.setDoctor(doctor);
		appointment.setPatient(patient);
		appointment.setAppointmentDate(form.getDate());
		appointment.setAppointmentTime(form.getTime());
		appointment.setSlotDuration(availability.getSlotDuration());
		appointment.setStatus(AppointmentStatus.PENDING);
		return this.saveAppointment(appointment);
	}
	
	public void cancelAppointment(Long apptId) {
		Appointment appointment = this.getAppointmentById(apptId);
		appointment.setStatus(AppointmentStatus.CANCELLED);
	}
	
	public Appointment saveAppointment(Appointment appointment) {
		return apptRepo.save(appointment);
	}
	
	public Appointment getAppointmentById(Long apptId) {
		return apptRepo.findById(apptId).orElse(null);
	}
	
	public Appointment nextPatientAppointment(Patient patient) {
		List<Appointment> next = apptRepo.findNextAppointmentForPatient(patient.getId(), LocalDate.now(), LocalTime.now());
		if(!next.isEmpty())
			return next.getFirst();
		return null;
	}
	
	public Appointment nextDoctorAppointment(Doctor doctor) {
		List<Appointment> nextOne = apptRepo.findNextAppointmentForDoctor(doctor.getId(), LocalDate.now(), LocalTime.now());
		if(!nextOne.isEmpty())
			return nextOne.getFirst();
		return null;
	}
	
}
