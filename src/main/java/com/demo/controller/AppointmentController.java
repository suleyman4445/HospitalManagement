package com.demo.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.dto.AppointmentDTO;
import com.demo.entity.Appointment;
import com.demo.entity.Appointment.AppointmentStatus;
import com.demo.entity.Doctor;
import com.demo.entity.DoctorAvailability;
import com.demo.entity.Patient;
import com.demo.entity.User;
import com.demo.service.AppointmentService;
import com.demo.service.DoctorAvailabilityService;
import com.demo.service.DoctorService;
import com.demo.service.PatientService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
	
	@Autowired
	private AppointmentService apptService;
	@Autowired
	private PatientService patService;
	@Autowired
	private DoctorService docService;
	@Autowired
	private DoctorAvailabilityService availService;
	
	@GetMapping("/book")
	public String showBookingForm(Model model) {
	    List<Doctor> doctors = docService.getAllDoctors();
	    
	    Map<Long, String> formattedAvailabilityMap = new HashMap<>();
	    formattedAvailabilityMap = doctors.stream()
	    	    .collect(Collectors.toMap(
	    	        Doctor::getId,
	    	        doc -> doc.getAvailDays().stream()
	    	                  .map(DoctorAvailability::getDayOfWeek)
	    	                  .distinct()
	    	                  .map(DayOfWeek::toString)
	    	                  .collect(Collectors.joining(", "))
	    	    ));

	    model.addAttribute("form", new AppointmentDTO());
	    model.addAttribute("doctors", doctors);
	    model.addAttribute("formattedAvailabilityMap", formattedAvailabilityMap);

	    return "patient/book-form";
	}

	
	@PostMapping("/book")
	public String processAppointment(@ModelAttribute("form") AppointmentDTO form, HttpSession session, Model model) {
		
		model.addAttribute("doctors",docService.getAllDoctors());
		if(form.getDoctorId() != null && form.getDate() != null) {
			List<LocalTime> slots = apptService.getAvailableSlots(form.getDoctorId(), form.getDate());
			
			if(slots.isEmpty()) {
				model.addAttribute("error", "No available slots for selected doctor on this Date");
				model.addAttribute("showSlots",false);
			}else {
				model.addAttribute("slots",slots);
				model.addAttribute("showSlots",true);
			}
		}
		
		model.addAttribute("form",form);
		return "patient/book-form";
	}
	
	
	@PostMapping("/confirm")
	public String confirmAppointment(@ModelAttribute("form") AppointmentDTO form, HttpSession session, 
			RedirectAttributes redirectAttributes) {
		
		try {
			User user = (User) session.getAttribute("loggedInUser");
			if(user == null)
				return "redirect:/login";
			
			apptService.confirmAppointment(user, form);
			redirectAttributes.addAttribute("success","Booking Completed Successfully");
			return "redirect:/patient/dashboard";
			
		}catch(DataIntegrityViolationException ex) {
			return "redirect:/error-page";
//			redirectAttributes.addAttribute("error","This Slot is Already Booked");
		}
		
	}
	
	@PostMapping("/cancel")
	public String cancelAppointment(@RequestParam Long id) {
		apptService.cancelAppointment(id);
		return "redirect:/patient/appointments";
	}
	
}
