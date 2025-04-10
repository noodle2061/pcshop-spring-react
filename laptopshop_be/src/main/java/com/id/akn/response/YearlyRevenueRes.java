package com.id.akn.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YearlyRevenueRes {
    private int year;
    private double totalRevenue;
}