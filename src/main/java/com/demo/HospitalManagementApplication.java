package com.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.demo.dto.AdminDTO;
import com.demo.repository.UserRepository;
import com.demo.service.AuthService;

@SpringBootApplication
public class HospitalManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalManagementApplication.class, args);
	}


	@Bean
	protected CommandLineRunner createDefaultAdmin(AuthService authService, UserRepository userRepo) {
		return args -> {
			if(!userRepo.existsByEmail("admin@hospital.com")) {
				AdminDTO dto = new AdminDTO();
				dto.setUsername("firstAdmin");
				dto.setName("Admin");
				dto.setEmail("admin@hospital.com");
				dto.setPassword("Admin123");
				authService.registerAdminUser(dto);
			}
		};
	}
}
