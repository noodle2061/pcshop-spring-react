package com.id.akn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Gpu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @NotNull(message = "GPU model cannot be empty")
    @Column(nullable = false)
    private String model;

    private String memory;

    private Short tops;
    
    @ManyToOne
    @JoinColumn(name = "brand_id")
    @JsonIgnore
    private Brand brand;

    @Column(nullable = false)
    private Type type;

    public enum Type {
        INTEGRATED,
        DISCRETE
    }
}

