package com.id.akn.controller;

import java.util.List;

import com.id.akn.exception.*;
import com.id.akn.request.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.id.akn.model.Address;
import com.id.akn.model.Order;
import com.id.akn.model.User;
import com.id.akn.service.OrderService;
import com.id.akn.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
	
	private OrderService orderService;
	private UserService userService;


	@PostMapping("/")
	public ResponseEntity<Order> createOrderHandler(@RequestBody OrderDTO orderDTO,
													@RequestHeader("Authorization") String jwt) throws UserException, CartItemException, ColorException, LaptopException {

		User user = userService.findUserProfileByJwt(jwt);
		Order order = orderService.createOrder(user, orderDTO);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<Address>> getAddressHandler(@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		List<Address> allUserAddress = user.getAddresses().stream().toList();
		return new ResponseEntity<>(allUserAddress, HttpStatus.OK);
	}

	@GetMapping("/user")
	public ResponseEntity<Page<Order>> userOrdersHistoryHandler(@RequestHeader("Authorization") String jwt,
																 @RequestParam(value = "status", required = false) Order.OrderStatus paymentStatus,
																 @RequestParam(value = "page", required = false, defaultValue = "1") int page,
																 @RequestParam(value = "size", required = false, defaultValue = "10") int size) throws UserException{
		
		User user=userService.findUserProfileByJwt(jwt);
		Page<Order> orders = orderService.userOrdersHistory(user.getId(), paymentStatus, page, size);
		return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity< Order> findOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") 
	String jwt) throws OrderException, UserException{
		User user = userService.findUserProfileByJwt(jwt);
		Order orders=orderService.findOrderById(orderId);
		return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
	}

	@PutMapping("/{orderId}")
	public ResponseEntity<Order> updateOrderStatus(
			@RequestHeader("Authorization") String jwt,
			@PathVariable Long orderId,
			@RequestParam(required = false) Order.OrderStatus orderStatus,
			@RequestParam(required = false) Order.PaymentStatus paymentStatus
	) throws OrderException, UserException {
		User user = userService.findUserProfileByJwt(jwt);
		Order updatedOrder = orderService.updateOrderStatus(orderId, user.getId(), orderStatus, paymentStatus);
		return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
	}

	@DeleteMapping("/{orderId}")
	public ResponseEntity<Void> deleteOrder(
			@RequestHeader("Authorization") String jwt,
			@PathVariable Long orderId
	) throws OrderException, UserException {
		User user = userService.findUserProfileByJwt(jwt);
		orderService.deleteOrder(orderId, user.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}