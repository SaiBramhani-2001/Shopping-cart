package com.focus.Shopping.cart.service.order;

import com.focus.Shopping.cart.model.Order;

public interface IOrderService  {
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);
}
