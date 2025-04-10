package com.id.akn.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LaptopColorDTO {
    private Integer id;
    private Integer laptopId;
    private String laptopModel;
    private Byte colorId;
    private String colorName;
    private Short quantity;
}