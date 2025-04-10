package com.id.akn.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyRevenueDTO {
    private int month;
    private double revenue;
}