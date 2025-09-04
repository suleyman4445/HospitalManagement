package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.AvailabilityDTO;
import com.demo.entity.Appointment;
import com.demo.entity.Doctor;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.service.AppointmentService;
import com.demo.service.DoctorService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
	
	@Autowired
	private DoctorService docService;
	@Autowired
	private AppointmentService apptService;
	
	@GetMapping("/dashboard")
	public String viewDashboard(HttpSession session, Model model) {
		User user = (User) session.getAttribute("loggedInUser");
		if(user==null || user.getRole() != Role.DOCTOR)
			return "redirect:/login";
		
		Doctor doctor = docService.getDoctorById(user.getId());
		List<Appointment> appointments = docService.getTodayAppointments(doctor.getId());
		
		model.addAttribute("doctor",doctor);
		Appointment nextAppointment = apptService.nextDoctorAppointment(doctor);
		model.addAttribute("nextAppointment",nextAppointment);
		model.addAttribute("appointments",appointments);
		return "doctor/dashboard";
	}
	
	@PutMapping("/{id}/update")
	public String updateDoctor(@PathVariable Long id, @ModelAttribute Doctor doctor) {
		docService.updateDoctor(id, doctor);
		return "redirect:/doctor/dashboard";
	}
	
	@GetMapping("/queue")
	public String viewTodayQueue(HttpSession session, Model model) {
		
		User user = (User) session.getAttribute("loggedInUser");
		if(user==null)
			return "redirect: /login";
		Doctor doctor = docService.getDoctorById(user.getId());
		if(doctor==null)
			return "redirect: /error-page";
		List<Appointment> appointments = docService.getTodayAppointments(doctor.getId());
		
		model.addAttribute("appointments",appointments);
		return "doctor/queue";
	}
	
	@GetMapping("/addAvailability")
	public String showAvailabilityForm(Model model) {
		model.addAttribute("availabilityDTO",new AvailabilityDTO());
		return "doctor/add-availability";
	}
	
	@PostMapping("/addAvailability")
	public String saveAvailability(@ModelAttribute AvailabilityDTO dto, HttpSession session) {
		
		User user = (User) session.getAttribute("loggedInUser");
		if(user == null || user.getRole()!=Role.DOCTOR)
			return "redirect:/login";
		
		Doctor doctor = docService.getDoctorById(user.getId());
		docService.saveOrUpdateAvailability(doctor, dto);
		return "redirect:/doctor/dashboard";
	}
	
	@GetMapping("/allAppointments")
	public String showAllAppointments(HttpSession session, Model model) {
		User user = (User) session.getAttribute("loggedInUser");
		if(user == null || user.getRole()!=Role.DOCTOR)
			return "redirect: /login";
		
//		return "under-development";
		List<Appointment> appointments = docService.getAppointmentsByDoctor(user.getId());
		model.addAttribute("appointments",appointments);
		return "doctor/view-all-appointments";
	}
	
}
