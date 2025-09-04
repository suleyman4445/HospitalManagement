package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.demo.dto.RegisterDTO;
import com.demo.entity.User;
import com.demo.repository.UserRepository;
import com.demo.service.AuthService;

import jakarta.servlet.http.HttpSession;

@Controller
//@RequestMapping("/api")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/login")
	public String showLoginForm(Model model) {
		model.addAttribute("user",new User());
		return "login";
	}
	
	
	@PostMapping("/login")
	public String login(@ModelAttribute User user,Model model,HttpSession session) {
		User loggedUser = authService.login(user.getEmail(), user.getPassword());
		
		if(loggedUser!=null) {
			session.setAttribute("loggedInUser",loggedUser);
			switch(loggedUser.getRole()) {
			case ADMIN: return "redirect:/admin/dashboard";
			case DOCTOR: return "redirect:/doctor/dashboard";
			case PATIENT: return "redirect:/patient/dashboard";
			}
		}
		model.addAttribute("error","Invalid Credentials");
		return "login";
	}
	
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("registerDTO",new RegisterDTO());
		return "register";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute("registerDTO") RegisterDTO registerDTO, Model model) {
		if(authService.isEmailExists(registerDTO.getEmail())) {
			model.addAttribute("error","Email Already Registered");
			return "register";
		}
		
		authService.registerPatientUser(registerDTO);
		return "redirect:/login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
	@GetMapping("/error-page")
	public String showErrorPage(HttpSession session, Model model) {
		User user = (User) session.getAttribute("loggedInUser");
		
		if(user!=null)
			model.addAttribute("role",user.getRole());
		else
			model.addAttribute("role",null);
		
		return "/error-page";
	}
	
}
