package com.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dto.AdminDTO;
import com.demo.dto.RegisterDTO;
import com.demo.entity.Admin;
import com.demo.entity.Appointment;
import com.demo.entity.Patient;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.repository.PatientRepository;
import com.demo.repository.UserRepository;

@Service
public class AuthService {

    private final AdminService adminService;
	
	@Autowired
	private UserRepository userRep;
	@Autowired
	private PatientRepository patRep;

    AuthService(AdminService adminService) {
        this.adminService = adminService;
    }
	
	public User login(String email,String password) {
		return userRep.findByEmailAndPassword(email, password).orElse(null);
	}
	
	public void registor(User user) {
		userRep.save(user);
	}
	
	public void registerPatientUser(RegisterDTO dto) {
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setRole(Role.PATIENT);
		userRep.save(user);
		
		Patient patient = new Patient();
		patient.setUser(user);
		patient.setName(dto.getName());
		patient.setAge(dto.getAge());
		patient.setGender(dto.getGender());
		patient.setAppointments(new ArrayList<>());
		patient.setAddress(dto.getAddress());
		patient.setPhone(dto.getPhone());
		patRep.save(patient);
		
	}
	public boolean isEmailExists(String email) {
		return userRep.findByEmail(email).isPresent();
	}
	
	public void registerAdminUser(AdminDTO dto) {
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setRole(Role.ADMIN);
		userRep.save(user);
		
		Admin admin = new Admin();
		admin.setUser(user);
		adminService.saveAdmin(admin);
	}
	
}
