package com.demo.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityDTO {
	
	private DayOfWeek day;
	private LocalTime startTime;
	private LocalTime endTime;
}
