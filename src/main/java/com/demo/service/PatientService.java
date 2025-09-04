package com.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entity.Appointment;
import com.demo.entity.Patient;
import com.demo.repository.AppointmentRepository;
import com.demo.repository.PatientRepository;

@Service
public class PatientService {
	
	@Autowired
	private PatientRepository patRep;
//	@Autowired
//	private AppointmentService apptService;
	
	@Autowired
	private AppointmentRepository apptRepo;
	
	public Patient updatePatient(Long id,Patient patient) {
		Patient existing = patRep.findById(id).orElse(null);
		if(existing==null)
			return null;
		existing.setName(patient.getName());
		existing.setAge(patient.getAge());
		existing.setGender(patient.getGender());
//		existing.setEmail(patient.getEmail());
		existing.setAddress(patient.getAddress());
		existing.setPhone(patient.getPhone());
		
		return patRep.save(existing);
	}
	
	public List<Appointment> getAppointmentsByPatient(Long patientId){
//		return apptService.getAppointmentsByPatient(patientId);
		return apptRepo.findByPatientId(patientId);
	}
	
	public Patient findPatientById(Long patientId){
		return patRep.findById(patientId).orElse(null);
	}
	
	
}
