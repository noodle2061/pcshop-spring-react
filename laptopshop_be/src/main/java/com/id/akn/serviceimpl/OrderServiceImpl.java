package com.id.akn.serviceimpl;

import java.time.LocalDateTime;
import java.util.*;

import com.id.akn.config.VNPAYConfig;
import com.id.akn.exception.*;
import com.id.akn.model.*;
import com.id.akn.repository.*;
import com.id.akn.request.CartItemDTO;
import com.id.akn.request.OrderDTO;
import com.id.akn.service.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
	
	private OrderRepository orderRepository;
	private CartItemService cartItemService;
	private AddressRepository addressRepository;
	private UserRepository userRepository;
	private OrderItemRepository orderItemRepository;
	private LaptopRepository laptopRepository;
	private LaptopService laptopService;
	private CartRepository cartRepository;
	private ColorService colorService;

	@Transactional
	@Override
	public Order createOrder(User user, OrderDTO orderDTO) throws UserException, LaptopException, ColorException, CartItemException {
		orderDTO.getShippingAddress().setUser(user);
		Address address = addressRepository.save(orderDTO.getShippingAddress());

		user.getAddresses().add(address);
		userRepository.save(user);

		List<OrderItem> orderItems = new ArrayList<>();
		float totalPrice = 0;
		float totalDiscountedPrice = 0;
		int totalItem = 0;

		for (CartItemDTO item : orderDTO.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			Laptop laptop = laptopRepository.findById(item.getLaptopId())
					.orElseThrow(() -> new LaptopException("Laptop not found"));
			orderItem.setLaptop(laptop);
			orderItem.setColor(colorService.getColorById(item.getColorId()));
			orderItem.setQuantity(item.getQuantity());

			// Tính tổng giá trị trước chiết khấu
			float itemTotalPrice = laptop.getPrice() * item.getQuantity();
			totalPrice += itemTotalPrice;

			// Tính giá trị chiết khấu cho sản phẩm
			float itemDiscountedPrice = itemTotalPrice * (100 - laptop.getDiscountPercent()) / 100;
			totalDiscountedPrice += itemDiscountedPrice;

			totalItem += item.getQuantity();

			cartItemService.removeCartItem(user.getId(), item.getId());
			OrderItem createdOrderItem = orderItemRepository.save(orderItem);
			orderItems.add(createdOrderItem);

			Color color = orderItem.getColor();
			Set<LaptopColor> laptopColors = laptop.getLaptopColors();
			for (LaptopColor laptopColor : laptopColors) {
				if (laptopColor.getColor().equals(color)) {
					laptopColor.setQuantity((short) (laptopColor.getQuantity() - orderItem.getQuantity()));
				}
			}
			laptopRepository.save(laptop);
		}

		Order createdOrder = new Order();
		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setShippingAddress(address);
		createdOrder.setTotalPrice(totalPrice);
		createdOrder.setTotalDiscountedPrice(totalDiscountedPrice);
		createdOrder.setTotalItem(totalItem);
		createdOrder.setCreatedAt(LocalDateTime.now());
		createdOrder.setPaymentMethod(orderDTO.getPaymentMethod());
		createdOrder.setPaymentStatus(Order.PaymentStatus.PENDING);
		createdOrder.setOrderStatus(Order.OrderStatus.PENDING);
		Order savedOrder = orderRepository.save(createdOrder);

		for (OrderItem item : orderItems) {
			item.setOrder(savedOrder);
			orderItemRepository.save(item);
		}

		return savedOrder;
	}


	@Override
	public Order findOrderById(Long orderId) throws OrderException {
		return orderRepository.findById(orderId).orElseThrow(() -> new OrderException("Order not foundwith id "+orderId));
	}

	@Override
	public Page<Order> userOrdersHistory(Long userId, Order.OrderStatus  paymentStatus, int page, int size)  {
		Pageable pageable = PageRequest.of(page - 1, size);
		if (paymentStatus != null) {
			return orderRepository.findByOrderStatusAndUserIdOrderByCreatedAtDesc(paymentStatus, userId, pageable);
		} else {
			return orderRepository.findByUserId(userId, pageable);
		}
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus(Order.OrderStatus.CONFIRMED);

		return orderRepository.save(order);
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus(Order.OrderStatus.SHIPPED);
		return orderRepository.save(order);
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus(Order.OrderStatus.DELIVERED);
		return orderRepository.save(order);
	}

	@Override
	public Order canceledOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		for (OrderItem orderItem : order.getOrderItems()) {
			Laptop laptop = orderItem.getLaptop();
			Color color = orderItem.getColor();
			Set<LaptopColor> laptopColors = laptop.getLaptopColors();
			for (LaptopColor laptopColor : laptopColors) {
				if(laptopColor.getColor().equals(color)) {
					laptopColor.setQuantity((short) (laptopColor.getQuantity()+orderItem.getQuantity()));
				}
			}

			laptopRepository.save(laptop);
		}
		order.setOrderStatus(Order.OrderStatus.CANCELLED);
		return orderRepository.save(order);
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAllByOrderByCreatedAtDesc();
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		orderRepository.deleteById(orderId);
	}
	@Override
	public void updatePaymentStatus(Long txnRef, Order.PaymentStatus status) throws OrderException {
		Order order = findOrderById(txnRef);
		if(status.equals(Order.PaymentStatus.FAILED)) {
			for (OrderItem orderItem : order.getOrderItems()) {
				Laptop laptop = orderItem.getLaptop();
				Color color = orderItem.getColor();
				Set<LaptopColor> laptopColors = laptop.getLaptopColors();
				for (LaptopColor laptopColor : laptopColors) {
					if(laptopColor.getColor().equals(color)) {
						laptopColor.setQuantity((short) (laptopColor.getQuantity()+orderItem.getQuantity()));
					}
				}

				laptopRepository.save(laptop);
			}
		}
		order.setPaymentStatus(status);
		orderRepository.save(order);
	}

	@Transactional
	@Override
	public Order updateOrderStatus(Long orderId, Long userId, Order.OrderStatus orderStatus, Order.PaymentStatus paymentStatus) throws OrderException {
		int rowsAffected = orderRepository.updateOrderStatus(orderId, userId, orderStatus, paymentStatus);
		if (rowsAffected > 0) {
			return orderRepository.findById(orderId).orElseThrow(() -> new OrderException("Order not found."));
		} else {
			throw new OrderException("Order not found or user not authorized to update this order.");
		}
	}

	@Transactional
	@Override
	public void deleteOrder(Long orderId, Long userId) {
		orderRepository.deleteById(orderId);
	}

}
