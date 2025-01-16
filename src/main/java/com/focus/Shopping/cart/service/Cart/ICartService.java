package com.focus.Shopping.cart.service.Cart;

import com.focus.Shopping.cart.model.Cart;

import java.math.BigDecimal;

public interface ICartService {


    Cart getCart(Long id);

    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart();
}
