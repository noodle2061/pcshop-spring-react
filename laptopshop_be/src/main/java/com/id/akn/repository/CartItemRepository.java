package com.id.akn.repository;

import com.id.akn.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.id.akn.model.Cart;
import com.id.akn.model.CartItem;
import com.id.akn.model.Laptop;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
	@Query("SELECT ci FROM CartItem ci WHERE ci.cart=:cart AND ci.laptop=:laptop AND ci.color.id=:color")
	CartItem isCartItemExist(@Param("cart") Cart cart, @Param("laptop") Laptop laptop, @Param("color") byte colorId);
	List<CartItem> findByLaptopAndColor(Laptop laptop, Color color);
}
