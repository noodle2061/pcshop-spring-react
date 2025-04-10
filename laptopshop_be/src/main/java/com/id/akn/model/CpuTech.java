package com.id.akn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpuTech {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @NotNull(message = "CPU technology cannot be empty")
    @Column(nullable = false)
    private String name;
}
