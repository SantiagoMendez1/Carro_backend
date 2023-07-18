package com.ingesoft.carro.controller;
import com.ingesoft.carro.model.CartModel;
import com.ingesoft.carro.model.CartItem;
import com.ingesoft.carro.service.interfaces.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartModel> getCart(@PathVariable("id") Long id) {
        CartModel cart = cartService.getCart(id);
        if (cart != null) {
            cart.setTotal(cart.getTotal());
            return ResponseEntity.ok(cart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CartModel> createCart() {
        CartModel createdCart = cartService.createCart();
        return ResponseEntity.ok(createdCart);
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<Void> addToCart(@PathVariable("id") Long id, @RequestBody CartItem item) {
        cartService.addToCart(id, item);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/removeitems/{itemId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable("id") Long id, @PathVariable("itemId") Long itemId) {
        cartService.removeFromCart(id, itemId);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{id}/quantity/{itemId}")
    public ResponseEntity<Void> updateCartItemQuantity(@PathVariable("id") Long id, @PathVariable("itemId") Long itemId, @RequestParam("quantity") Integer quantity) {
        cartService.updateCartItemQuantity(id, itemId, quantity);
        return ResponseEntity.ok().build();
    }
    
    
}







