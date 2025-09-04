package com.demo.service;

import java.time.DayOfWeek;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dto.AvailabilityDTO;
import com.demo.entity.Doctor;
import com.demo.entity.DoctorAvailability;
import com.demo.repository.DoctorAvailabilityRepository;

@Service
public class DoctorAvailabilityService {
	
	@Autowired
	private DoctorAvailabilityRepository docAvailRepo;
	
	public DoctorAvailability getDoctorAvailabilityByDay(Long doctorId, DayOfWeek day) {
		return docAvailRepo.findByDoctorIdAndDayOfWeek(doctorId, day).orElse(null);
	}
	
}
