package com.ingesoft.carro.service.impl;

import com.ingesoft.carro.model.CartItem;
import com.ingesoft.carro.model.CartModel;
import com.ingesoft.carro.service.interfaces.CartService;
import com.ingesoft.carro.service.interfaces.IdGeneratorService;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit; 

@Service
public class CartServiceImpl implements CartService {

    private final RedisService redisService;
    private final IdGeneratorService idGeneratorService;

    @Autowired
    public CartServiceImpl(RedisService redisService, IdGeneratorService idGeneratorService) {
        this.redisService = redisService;
        this.idGeneratorService = idGeneratorService;
    }

    @Override
    public CartModel createCart() {
        CartModel cart = new CartModel(idGeneratorService.generateCartId(), new ArrayList<>());
        redisService.set("CART", cart.getId().toString(), cart, 1, TimeUnit.HOURS);
        return cart;
    }

    @Override
    public CartModel getCart(Long id) {
        return redisService.get("CART", id.toString());
    }

    @Override
    public void addToCart(Long id, CartItem item) {
        CartModel cart = getCart(id);
        if (cart == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found with ID: " + id);
        }
        Long generatedItemId = idGeneratorService.generateItemId();
        item.setItemId(generatedItemId);
        cart.getItems().add(item);
        redisService.set("CART", id.toString(), cart, 1, TimeUnit.HOURS);;
    }

    @Override
    public void removeFromCart(Long id, Long itemId) {
        CartModel cart = getCart(id);
        if (cart != null) {
        List<CartItem> items = cart.getItems();
        items.removeIf(item -> item.getItemId().equals(itemId));
        redisService.set("CART", id.toString(), cart, 1, TimeUnit.HOURS);
    }
    }
    
    @Override
    public void updateCartItemQuantity(Long id, Long itemId, Integer quantity) {
        CartModel cart = getCart(id);
        if (cart != null) {
            List<CartItem> items = cart.getItems();
            for (CartItem item : items) {
                if (item.getItemId().equals(itemId)) {
                    item.setQuantity(quantity);
                    break;
                }
            }
            redisService.set("CART", id.toString(), cart, 1, TimeUnit.HOURS);
        }
    }


}
