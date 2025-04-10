package com.id.akn.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String review;

	@ManyToOne
	@JoinColumn(name = "laptop_id")
	@JsonIgnore
	private Laptop laptop;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;	
	
	private LocalDateTime createdAt;
}
