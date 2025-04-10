package com.id.akn.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BudgetRes {

    private Long totalUser;
    private Double totalRevenue;
    private Long totalProduct;
    private Long totalOrder;

}
