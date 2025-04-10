package com.id.akn.controller;

import com.id.akn.exception.UserException;
import com.id.akn.model.User;
import com.id.akn.response.BudgetRes;
import com.id.akn.response.ProductRevenuePercentageDTO;
import com.id.akn.response.YearlyRevenueDTO;
import com.id.akn.response.YearlyRevenueRes;
import com.id.akn.service.RevenueStatisticsService;
import com.id.akn.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@AllArgsConstructor
public class DashboardController {

    private final RevenueStatisticsService revenueStatisticsService;
    private final UserService userService;

    @GetMapping("/yearly-revenue")
    public ResponseEntity<List<YearlyRevenueRes>> getYearlyRevenue() {
        List<YearlyRevenueRes> yearlyRevenues = revenueStatisticsService.calculateTotalRevenuePerYear();
        return ResponseEntity.ok(yearlyRevenues);
    }

    @GetMapping("/yearly")
    public ResponseEntity<YearlyRevenueDTO> getYearlyRevenue(@RequestParam int year, @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        YearlyRevenueDTO yearlyRevenue = revenueStatisticsService.calculateYearlyRevenueWithMonthlyData(year);
        return ResponseEntity.ok(yearlyRevenue);
    }


    @GetMapping("/product-revenue-percentages")
    public ResponseEntity<List<ProductRevenuePercentageDTO>> getProductRevenuePercentages(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        List<ProductRevenuePercentageDTO> productRevenuePercentages = revenueStatisticsService.calculateProductRevenuePercentages();
        return ResponseEntity.ok(productRevenuePercentages);
    }

    @GetMapping("/budget")
    public ResponseEntity<BudgetRes> getTotalBudget(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        BudgetRes budgetRes = revenueStatisticsService.getBudgetRes();
        return ResponseEntity.ok(budgetRes);
    }

}
