package com.id.akn.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.id.akn.model.Gpu;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GpuDTO {
    private Short id;
    @NotNull(message = "GPU model cannot be empty")
    private String model;
    private String memory;
    private Short tops;
    private Byte brandId;
    private String brandName;
    private Gpu.Type type;
//    @JsonCreator
//    public GpuDTO(@JsonProperty("id") Short id) {
//        this.id = id;
//    }
}
