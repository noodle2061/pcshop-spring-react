package com.id.akn.service;

import com.id.akn.exception.*;
import com.id.akn.model.Cart;
import com.id.akn.model.CartItem;
import com.id.akn.model.User;
import com.id.akn.request.CartItemDTO;

import java.util.List;

public interface CartService {
	Cart createCart(User user);
	CartItemDTO addItemToCart(Long userId, CartItemDTO req) throws LaptopException, OsVersionException, BrandException, CpuException, ColorException, CartItemException, UserException;
	List<CartItemDTO> findUserCart(Long userId);
}
