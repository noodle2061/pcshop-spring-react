package com.id.akn.controller;

import com.id.akn.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.id.akn.model.User;
import com.id.akn.request.CartItemDTO;
import com.id.akn.response.ApiRes;
import com.id.akn.service.CartService;
import com.id.akn.service.UserService;

import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
	
	private CartService cartService;
	private UserService userService;
	
	@GetMapping("/")
	public ResponseEntity<List<CartItemDTO>> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws UserException{
		User user=userService.findUserProfileByJwt(jwt);
		List<CartItemDTO> allCartItems = cartService.findUserCart(user.getId());
		return new ResponseEntity<>(allCartItems,HttpStatus.OK);
	}
	
	@PutMapping("/add")
	public ResponseEntity<ApiRes> userAddItemToCart(@RequestBody CartItemDTO req,
                                                    @RequestHeader("Authorization") String jwt) throws UserException, LaptopException, OsVersionException, ColorException, BrandException, CpuException, CartItemException {
		User user=userService.findUserProfileByJwt(jwt);
		CartItemDTO item = cartService.addItemToCart(user.getId(), req);
		ApiRes res= new ApiRes("Item Added To Cart Successfully",true);
		return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
	}
}
