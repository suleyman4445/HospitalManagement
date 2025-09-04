package com.demo.service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dto.AvailabilityDTO;
import com.demo.entity.Appointment;
import com.demo.entity.Doctor;
import com.demo.entity.DoctorAvailability;
import com.demo.repository.AppointmentRepository;
import com.demo.repository.DoctorAvailabilityRepository;
import com.demo.repository.DoctorRepository;

@Service
public class DoctorService {
	
	@Autowired
	private DoctorRepository docRep;
//	@Autowired
//	private AppointmentService apptService;
	@Autowired
	private AppointmentRepository apptRepo;
	@Autowired
	private DoctorAvailabilityRepository docAvailRepo;
	
	
	public Doctor getDoctorById(Long id) {
		return docRep.findById(id).orElse(null);
	}
	
	public List<Doctor> getAllDoctors(){
		return docRep.findAll();
	}
	
	public Doctor updateDoctor(Long id,Doctor doctor) {
		Doctor existing = this.getDoctorById(id);
		if(existing!=null) {
			existing.setName(doctor.getName());
			existing.setAvailDays(doctor.getAvailDays());
			existing.setDept(doctor.getDept());
//			existing.setStartTime(doctor.getStartTime());
//			existing.setEndTime(doctor.getEndTime());
			existing.setConsulFee(doctor.getConsulFee());
			return docRep.save(existing);
		}
		return null;
	}
	
	public List<Appointment> getAppointmentsByDoctor(Long doctorId){
//		return apptService.getAppointmentsByDoctor(doctorId);
		return apptRepo.findByDoctorId(doctorId);
	}
	
	public void saveOrUpdateAvailability(Doctor doctor, AvailabilityDTO dto) {
		Optional<DoctorAvailability> optional = docAvailRepo.findByDoctorIdAndDayOfWeek(doctor.getId(), dto.getDay());
		
		DoctorAvailability availability = optional.orElse(new DoctorAvailability());
		availability.setDoctor(doctor);
		availability.setDayOfWeek(dto.getDay());
		availability.setStartTime(dto.getStartTime());
		availability.setEndTime(dto.getEndTime());
		availability.setSlotDuration(20);
		doctor.getAvailDays().add(availability);
		
		docAvailRepo.save(availability);
	}
	
	public List<Appointment> getTodayAppointments(Long doctorId){
		LocalDate today = LocalDate.now();
		return apptRepo.findByDoctor_IdAndAppointmentDate(doctorId, today);
	}
	
}
