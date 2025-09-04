package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{
	
	
}
