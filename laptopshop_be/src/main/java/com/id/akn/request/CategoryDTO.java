package com.id.akn.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Byte id;
    @NotNull(message = "Category name cannot be empty")
    @Size(max = 50)
    private String name;
}

