package com.id.akn.serviceimpl;

import com.id.akn.model.Category;
import com.id.akn.model.Order;
import com.id.akn.repository.LaptopRepository;
import com.id.akn.repository.OrderRepository;
import com.id.akn.repository.UserRepository;
import com.id.akn.response.*;
import com.id.akn.service.RevenueStatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RevenueStatisticsServiceImpl implements RevenueStatisticsService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final LaptopRepository laptopRepository;

    @Override
    public List<YearlyRevenueRes> calculateTotalRevenuePerYear() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getCreatedAt().getYear(),
                        Collectors.summingDouble(Order::getTotalPrice)
                ))
                .entrySet().stream()
                .map(entry -> new YearlyRevenueRes(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public YearlyRevenueDTO calculateYearlyRevenueWithMonthlyData(int year) {
        List<Order> orders = orderRepository.findAll(); // Hoặc filter theo thời gian cụ thể.

        // Filter orders by year
        List<Order> filteredOrders = orders.stream()
                .filter(order -> order.getCreatedAt().getYear() == year)
                .collect(Collectors.toList());

        Map<Integer, Double> monthlyRevenueMap = filteredOrders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getCreatedAt().getMonthValue(),
                        Collectors.summingDouble(Order::getTotalPrice)
                ));

        List<MonthlyRevenueDTO> monthlyRevenueList = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            double revenue = monthlyRevenueMap.getOrDefault(month, 0.0);
            monthlyRevenueList.add(new MonthlyRevenueDTO(month, revenue));
        }

        double yearlyTotalRevenue = monthlyRevenueList.stream()
                .mapToDouble(MonthlyRevenueDTO::getRevenue)
                .sum();

        return new YearlyRevenueDTO(year, yearlyTotalRevenue, monthlyRevenueList);
    }

    public List<ProductRevenuePercentageDTO> calculateProductRevenuePercentages() {
        List<Order> orders = orderRepository.findAll();

        Map<String, Double> productRevenue = orders.stream()
                .flatMap(order -> order.getOrderItems().stream())
                .collect(Collectors.groupingBy(
                        orderItem -> orderItem.getLaptop().getCategories().stream()
                                .map(Category::getName)
                                .findFirst()
                                .orElse("Unknown"),
                        Collectors.summingDouble(item -> item.getLaptop().getPrice() * item.getQuantity())
                ));

        double totalRevenue = orders.stream()
                .flatMap(order -> order.getOrderItems().stream())
                .mapToDouble(item -> item.getLaptop().getPrice() * item.getQuantity())
                .sum();

        return productRevenue.entrySet().stream()
                .map(entry -> new ProductRevenuePercentageDTO(entry.getKey(), (entry.getValue() / totalRevenue) * 100))
                .collect(Collectors.toList());
    }


    @Override
    public BudgetRes getBudgetRes() {
        BudgetRes budgetRes = new BudgetRes(userRepository.totalUser(), orderRepository.getTotalRevenue(), laptopRepository.getTotalProduct(), orderRepository.getTotalOrder());
        return budgetRes;
    }
}
