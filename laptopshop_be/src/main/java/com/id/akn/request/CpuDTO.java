package com.id.akn.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpuDTO {
    private Short id;
    private Byte brandId;
    private String brandName;
    private Short technologyId;
    private String technologyName;
    @NotNull
    private String model;
    private Float speed;
    private Float maxSpeed;
    private Byte core;
    private Byte thread;
    private Float cache;
    private Short tops;
}
