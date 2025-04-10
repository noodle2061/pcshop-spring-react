package com.id.akn.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	@ManyToOne
	private Order order;
	
	@ManyToOne
	private Laptop laptop;

	@ManyToOne
	//@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	//@JoinColumn(name = "color_id", nullable = false)
	private Color color;

	@Column(nullable = false)
	private short quantity;

}
