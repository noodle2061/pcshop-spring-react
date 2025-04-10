package com.id.akn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Không được để trống ")
	@Column(nullable = false)
	private String name;

	@NotBlank(message = "Không được để trống ")
	@Column(name="street_address", nullable = false)
	private String streetAddress;

	@NotBlank(message = "Không được để trống ")
	@Column(nullable = false)
	private String city;

	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonIgnore
	private User user;


	@Column(name = "phone_number",nullable = false)
	@NotBlank(message = "Số điện thoại không được để trống")
	@Pattern(regexp = "0\\d{9,10}", message = "Số điện thoại phải bắt đầu bằng số 0 và có độ dài từ 10 đến 11 chữ số")
	private String phoneNumber;	
}
