package com.id.akn.service;

import com.id.akn.response.BudgetRes;
import com.id.akn.response.ProductRevenuePercentageDTO;
import com.id.akn.response.YearlyRevenueDTO;
import com.id.akn.response.YearlyRevenueRes;

import java.util.List;

public interface RevenueStatisticsService {
    List<YearlyRevenueRes> calculateTotalRevenuePerYear();
    YearlyRevenueDTO calculateYearlyRevenueWithMonthlyData(int year);
    List<ProductRevenuePercentageDTO> calculateProductRevenuePercentages();
    BudgetRes getBudgetRes();
}
