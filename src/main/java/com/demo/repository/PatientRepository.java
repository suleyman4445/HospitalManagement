package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entity.Patient;
import com.demo.entity.User;

public interface PatientRepository extends JpaRepository<Patient,Long>{
	
}
