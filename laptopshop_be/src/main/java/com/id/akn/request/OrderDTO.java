package com.id.akn.request;

import com.id.akn.model.Address;
import com.id.akn.model.CartItem;
import com.id.akn.model.Order;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Address shippingAddress;
    private Order.PaymentMethod paymentMethod;
    private List<CartItemDTO> cartItems;
}

