package com.id.akn.service;

import java.util.List;

import com.id.akn.exception.*;
import com.id.akn.model.Order;
import com.id.akn.model.User;
import com.id.akn.request.OrderDTO;
import org.springframework.data.domain.Page;

public interface OrderService {
	Order createOrder(User user, OrderDTO orderDTO) throws CartItemException, UserException, LaptopException, ColorException;
	Order findOrderById(Long orderId) throws OrderException;
	//Order findOrderByOrderId(String orderId) throws OrderException;
	Page<Order> userOrdersHistory(Long userId, Order.OrderStatus paymentStatus, int page, int size);
	Order confirmedOrder(Long orderId) throws OrderException;
	Order shippedOrder(Long orderId) throws OrderException;
	Order deliveredOrder(Long orderId) throws OrderException;
	Order canceledOrder(Long orderId) throws OrderException;
	List<Order> getAllOrders();
	void deleteOrder(Long orderId) throws OrderException;
	void updatePaymentStatus(Long txnRef, Order.PaymentStatus status) throws OrderException;
	Order updateOrderStatus(Long orderId, Long userId, Order.OrderStatus newStatus, Order.PaymentStatus paymentStatus) throws OrderException;
	void deleteOrder(Long orderId, Long userId) throws OrderException;
}
