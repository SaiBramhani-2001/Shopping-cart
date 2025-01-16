package com.focus.Shopping.cart.repository;


import com.focus.Shopping.cart.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
