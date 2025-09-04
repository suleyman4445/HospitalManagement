package com.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Admins")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
	
	@Id
	private Long id;
	private String name;
	
	@OneToOne
	@MapsId
	@JoinColumn(name="adminId_FK")
	private User user;
}
