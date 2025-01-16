package com.focus.Shopping.cart.service.Cart;

import com.focus.Shopping.cart.exceptions.ResourceNotFoundException;
import com.focus.Shopping.cart.model.Cart;
import com.focus.Shopping.cart.repository.CartItemRepository;
import com.focus.Shopping.cart.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong CartIdGenerator = new AtomicLong(0);

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return  cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
         return cart.getTotalAmount();
    }

    @Override
    public Long initializeNewCart() {
         Cart newCart = new Cart();
         Long newCartId = CartIdGenerator.incrementAndGet();
         newCart.setId(newCartId);
         return cartRepository.save(newCart).getId();
    }
}
