package com.focus.Shopping.cart.repository;

import com.focus.Shopping.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    void deleteAllByCartId(Long id);
}
