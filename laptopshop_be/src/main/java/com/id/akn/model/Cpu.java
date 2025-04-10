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
public class Cpu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @ManyToOne
    @JoinColumn(name = "technology_id")
    private CpuTech technology;

    @NotNull(message = "CPU model cannot be empty")
    @Column(nullable = false)
    private String model;

    private Float speed;

    @Column(name = "max_speed")
    private Float maxSpeed;

    private Byte core;

    private Byte thread;

    private Float cache;

    private Short tops;
}