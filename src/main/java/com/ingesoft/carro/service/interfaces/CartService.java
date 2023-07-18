package com.ingesoft.carro.service.interfaces;

import java.util.List;

import com.ingesoft.carro.model.CartItem;
import com.ingesoft.carro.model.CartModel;

public interface CartService {
    CartModel getCart(Long id);
    CartModel createCart();
    void addToCart(Long id, CartItem item);
    void updateCartItemQuantity(Long id, Long itemId, Integer quantity);
    void removeFromCart(Long id, Long itemId);
}

   


