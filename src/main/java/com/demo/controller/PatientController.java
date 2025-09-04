package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.entity.Appointment;
import com.demo.entity.Patient;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.service.AppointmentService;
import com.demo.service.PatientService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/patient")
public class PatientController {
		
	@Autowired
	private PatientService patService;
	@Autowired
	private AppointmentService apptService;
	
	@GetMapping("/dashboard")
	public String viewDashboard(HttpSession session, Model model) {
		User user = (User) session.getAttribute("loggedInUser");
		if(user == null || user.getRole()!=Role.PATIENT)
			return "redirect:/login";
		
		Patient patient = patService.findPatientById(user.getId());
		model.addAttribute("patient",patient);
		
		Appointment nextAppointment = apptService.nextPatientAppointment(patient);
		model.addAttribute("nextAppointment",nextAppointment);
		
		return "patient/dashboard";
	}
	
	@GetMapping("/appointments")
	public String viewAppointments(HttpSession session, Model model) {
		User user = (User) session.getAttribute("loggedInUser");
		if(user==null || user.getRole()!=Role.PATIENT)
			return "redirect: /login";
		
		Patient patient = patService.findPatientById(user.getId());
		List<Appointment> appointments = apptService.getAppointmentsByPatient(patient.getId());
		
		model.addAttribute("appointments",appointments);
		return "patient/appointments";
	}
	
}
