package com.id.akn.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class YearlyRevenueDTO {
    private int year;
    private double totalRevenue;
    private List<MonthlyRevenueDTO> monthlyRevenueList;
}