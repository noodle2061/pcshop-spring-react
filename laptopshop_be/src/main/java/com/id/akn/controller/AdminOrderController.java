package com.id.akn.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.id.akn.exception.OrderException;
import com.id.akn.model.Order;
import com.id.akn.response.ApiRes;
import com.id.akn.service.OrderService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
	
	private OrderService orderService;
	
	@GetMapping("/")
	public ResponseEntity<List<Order>> getAllOrdersHandler(){
		List<Order> orders=orderService.getAllOrders();
		
		return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/confirmed")
	public ResponseEntity<Order> confirmedOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException{
		Order order=orderService.confirmedOrder(orderId);
		return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/ship")
	public ResponseEntity<Order> shippedOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException{
		Order order=orderService.shippedOrder(orderId);
		return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/deliver")
	public ResponseEntity<Order> deliveredOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException{
		Order order=orderService.deliveredOrder(orderId);
		return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<Order> canceledOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException{
		Order order=orderService.canceledOrder(orderId);
		return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{orderId}/delete")
	public ResponseEntity<ApiRes> deleteOrderHandler(@PathVariable Long orderId,
                                                     @RequestHeader("Authorization") String jwt) throws OrderException{
		orderService.deleteOrder(orderId);
		ApiRes res=new ApiRes("Order deleted successfully",true);
		System.out.println("Delete method working....");
		return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
	}
}

