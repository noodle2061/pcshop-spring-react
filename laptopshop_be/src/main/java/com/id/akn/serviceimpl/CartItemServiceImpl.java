package com.id.akn.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.id.akn.model.*;
import com.id.akn.request.CartItemDTO;
import com.id.akn.service.CartItemService;
import com.id.akn.service.UserService;
import org.springframework.stereotype.Service;

import com.id.akn.exception.CartItemException;
import com.id.akn.exception.UserException;
import com.id.akn.repository.CartItemRepository;
import com.id.akn.repository.CartRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartItemServiceImpl implements CartItemService {
	private CartItemRepository cartItemRepository;
	private UserService userService;
	private CartRepository cartRepository;
	
	@Override
	public CartItem createCartItem(CartItem cartItem) {
		//cartItem.setQuantity(1);
		//cartItem.setPrice(cartItem.getLaptop().getPrice()*cartItem.getQuantity());
		//cartItem.setDiscountPercent(cartItem.getLaptop().getDiscountPercent());
		cartItem.setAddAt(LocalDateTime.now());
		return cartItemRepository.save(cartItem); // Trả về thực thể Managed
	}

	@Override
	public CartItemDTO updateCartItem(Long userId, String id, CartItemDTO cartItem) throws CartItemException, UserException {
		CartItem updateItem = findCartItemById(id);
		User user = userService.findUserById(userId);
		if(user.getId().equals(userId)) {
			updateItem.setQuantity(cartItem.getQuantity());
		}
		return convertToDTO(cartItemRepository.save(updateItem));
	}

	@Override
	public CartItemDTO isCartItemExist(Cart cart, Laptop laptop, Byte color) {
		CartItem cartItem = cartItemRepository.isCartItemExist(cart, laptop, color);
		if (cartItem == null) {
			return null;
		}
		return convertToDTO(cartItem);
	}

	@Override
	public void removeCartItem(Long userId, String cartItemId) throws UserException, CartItemException {
		User user = userService.findUserById(userId);
		Cart cart = cartRepository.findByUserId(userId);
		User reqUser = userService.findUserById(userId);
		if(user.getId().equals(reqUser.getId())) {
			cart.getCartItems().remove(findCartItemById(cartItemId));
			cartItemRepository.deleteById(cartItemId);
			cartRepository.save(cart);
		}
		else {
			throw new UserException("You can't remove another users item!");
		}
	}

	@Override
	public CartItem findCartItemById(String cartItemId) throws CartItemException {
		return cartItemRepository.findById(cartItemId)
				.orElseThrow(() -> new CartItemException("CartItem not found!"));
	}

//	@Override
//	public List<CartItem> findAllCartItemByLaptopAndColor(Laptop laptop, Color color) {
//		return cartItemRepository.findByLaptopAndColor(laptop, color);
//	}

	public CartItemDTO convertToDTO(CartItem cartItem) {
		CartItemDTO cartItemDTO = new CartItemDTO();
		// Set basic fields
		cartItemDTO.setId(cartItem.getId());
		cartItemDTO.setLaptopId(cartItem.getLaptop().getId());
		cartItemDTO.setLaptopModel(cartItem.getLaptop().getModel());
		cartItemDTO.setLaptopPrice(cartItem.getLaptop().getPrice());
		cartItemDTO.setDiscountPercent(cartItem.getLaptop().getDiscountPercent());
		cartItemDTO.setColorId(cartItem.getColor().getId());
		cartItemDTO.setColorName(cartItem.getColor().getName());
		cartItemDTO.setQuantity(cartItem.getQuantity());

		// Get the first image URL
		Set<String> imageUrls = cartItem.getLaptop().getImageUrls(); // Assuming this method exists
		if (imageUrls != null && !imageUrls.isEmpty()) {
			// Use iterator to get the first element from the Set
			String firstImageUrl = imageUrls.iterator().next();
			cartItemDTO.setFirstImageUrl(firstImageUrl);
		} else {
			cartItemDTO.setFirstImageUrl(null);
		}


		return cartItemDTO;
	}

}
