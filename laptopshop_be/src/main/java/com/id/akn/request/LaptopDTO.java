package com.id.akn.request;

import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LaptopDTO {
    private Integer id;
    private Byte brandId;
    private String brandName;
    @NotNull
    private String model;
    private CpuDTO cpu;
    private Set<GpuDTO> gpus;
    private byte ramMemory;
    private String ramDetail;
    private short diskCapacity;
    private String diskDetail;
    private float screenSize;
    private String screenDetail;
    private Short osVersionId;
    private String osVersion;
    private String keyboardType;
    private String batteryCharger;
    private String design;
    private Set<LaptopColorDTO> laptopColors;
    private Set<CategoryDTO> categories;
    private String origin;
    private byte warranty;
    private long price;
    private Set<String> imageUrls;
    private float discountPercent;
    private Short status;
}
