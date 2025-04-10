package com.id.akn.service;

import com.id.akn.exception.CartItemException;
import com.id.akn.exception.UserException;
import com.id.akn.model.Cart;
import com.id.akn.model.CartItem;
import com.id.akn.model.Color;
import com.id.akn.model.Laptop;
import com.id.akn.request.CartItemDTO;

import java.util.List;

public interface CartItemService {
	 CartItem createCartItem(CartItem cartItem);
	 CartItemDTO updateCartItem(Long userId, String id, CartItemDTO cartItem) throws CartItemException, UserException;
	 CartItemDTO isCartItemExist(Cart cart, Laptop laptop, Byte colorId);
	 void removeCartItem(Long userId, String cartItemId) throws UserException, CartItemException;
	 CartItem findCartItemById(String cartItemId) throws CartItemException;
	 //List<CartItem> findAllCartItemByLaptopAndColor(Laptop laptop, Color color) throws CartItemException;
}
