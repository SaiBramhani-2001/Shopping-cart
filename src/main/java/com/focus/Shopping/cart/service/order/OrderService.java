package com.focus.Shopping.cart.service.order;

import com.focus.Shopping.cart.enums.OrderStatus;
import com.focus.Shopping.cart.exceptions.ResourceNotFoundException;
import com.focus.Shopping.cart.model.Cart;
import com.focus.Shopping.cart.model.Order;
import com.focus.Shopping.cart.model.OrderItem;
import com.focus.Shopping.cart.model.Product;
import com.focus.Shopping.cart.repository.OrderRepository;
import com.focus.Shopping.cart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;



    @Override
    public Order placeOrder(Long userId) {
        return null;
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setOrderStatus(OrderStatus.PENDING );
        order.setOrderDate(LocalDate.now());
        return order;
    }


    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory()-cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    order,product,cartItem.getQuantity(),cartItem.getUnitPrice()
            );
        }).toList();

    }



    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        return orderItemList
                .stream()
                .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }




    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order not found"));

    }
}
