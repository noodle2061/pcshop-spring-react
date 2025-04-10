package com.id.akn.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaptopColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "laptop_id")
    @JsonIgnore
    private Laptop laptop;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @Column(nullable = false)
    private short quantity;
}


