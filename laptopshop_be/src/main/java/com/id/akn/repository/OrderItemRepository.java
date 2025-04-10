package com.id.akn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.id.akn.model.OrderItem;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
