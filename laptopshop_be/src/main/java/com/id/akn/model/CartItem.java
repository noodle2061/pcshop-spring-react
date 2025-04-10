package com.id.akn.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
	@Id
	@Column(nullable = false, unique = true)
	private String id; // ID dạng chuỗi kết hợp giữa userId và laptopId

	@ManyToOne
	@JsonIgnore
	private Cart cart;
	
	@ManyToOne
	private Laptop laptop;

	@ManyToOne
	private Color color;

	private short quantity;

	@Column(name="add_at")
	private LocalDateTime addAt;

	public void generateCompositeId() {
		if (this.cart.getUser().getId() != null && this.laptop != null && this.color != null) {
			this.id = this.cart.getUser().getId() + "_" + this.laptop.getId() +"_" + this.color.getId();
		}
	}
}
