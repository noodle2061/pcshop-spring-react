package com.id.akn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;
import java.util.HashSet;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Byte id;

	@NotNull(message = "Category name cannot be empty")
	@Size(max = 50)
	@Column(unique = true, nullable = false)
	private String name;

//	@ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)
//	@JsonIgnore
//	private Set<Laptop> laptops = new HashSet<>();
}