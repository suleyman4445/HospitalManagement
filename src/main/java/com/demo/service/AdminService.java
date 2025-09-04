package com.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dto.DoctorDTO;
import com.demo.entity.Admin;
import com.demo.entity.Doctor;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.repository.AdminRepository;
import com.demo.repository.DoctorRepository;
import com.demo.repository.UserRepository;

@Service
public class AdminService {
	
	@Autowired
	private UserRepository userRep;
	@Autowired
	private DoctorRepository docRep;
	@Autowired
	private AdminRepository adminRepo;
	
	public void saveAdmin(Admin admin) {
		adminRepo.save(admin);
	}
	
	public List<Doctor> getAllDoctors(){
		return docRep.findAll();
	}
	
	public Doctor addDoctor(DoctorDTO dto) {
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setRole(Role.DOCTOR);
		userRep.save(user);
		
		Doctor doctor = new Doctor();
		doctor.setUser(user);
		doctor.setName(dto.getName());
		doctor.setDept(dto.getDept());
		doctor.setPhone(dto.getPhone());
		doctor.setConsulFee(0.0);
		doctor.setAppointments(new ArrayList<>());
		doctor.setAvailDays(new ArrayList<>());
		
		return docRep.save(doctor);
	}
	
	public Doctor getDoctorById(Long id) {
		return docRep.findById(id).orElse(null);
	}
	
	public void deleteDoctor(Long id) {
		docRep.deleteById(id);
	}
	
	
}
