package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.dto.DoctorDTO;
import com.demo.entity.Doctor;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.service.AdminService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@GetMapping("/dashboard")
	public String viewDashboard(HttpSession session, Model model) {
		User user = (User) session.getAttribute("loggedInUser");
		if(user==null || user.getRole()!=Role.ADMIN)
			return "redirect:/login";
		
		List<Doctor> doctors = adminService.getAllDoctors();
		model.addAttribute("doctors",doctors);
		return "admin/dashboard";
	}
	
//	@GetMapping("/doctors")
//	public String getAllDoctors(HttpSession session, Model model){
//		User user = (User) session.getAttribute("loggedInUser");
//		if(user==null || user.getRole()!=Role.ADMIN)
//			return "redirect:/login";
//		List<Doctor> doctors = adminService.getAllDoctors();
//		model.addAttribute("doctors",doctors);
//		return "doctor/dashboard";
//	}
	
	@GetMapping("/doctor/{id}")
	public Doctor getDoctorById(@PathVariable Long id) {
		return adminService.getDoctorById(id);
	}
	
	@GetMapping("/addDoctor")
	public String showAddDoctorForm(Model model) {
		model.addAttribute("doctorDTO",new DoctorDTO());
		return "admin/add-doctor";
	}
	
	@PostMapping("/addDoctor")
	public String addDoctor(@ModelAttribute("doctorDTO") DoctorDTO doctorDTO) {
		adminService.addDoctor(doctorDTO);
		return "redirect:/admin/dashboard";
	}
	
	@GetMapping("/deleteDoctor/{id}")
	public String deleteDoctor(@PathVariable Long doctorId) {
		adminService.deleteDoctor(doctorId);
		return "redirect:/admin/dashboard";
	}
	
	
}
