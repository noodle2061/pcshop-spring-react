package com.id.akn.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpuTechDTO {
    private Short id;
    private Byte brandId;
    private String brandName;
    @NotNull(message = "CPU technology cannot be empty")
    private String techName;
}
