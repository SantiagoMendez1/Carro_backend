package com.ingesoft.carro.model;
import java.io.Serializable;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartModel implements Serializable {
    private static final long serialVersionUID = -8783783030860258094L;
    private Long id;
    private List<CartItem> items;
    private Float total;

    public CartModel(Long id, List<CartItem> items) {
        this.id = id;
        this.items = items;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public Float getTotal() {
        float total = 0;
        for (CartItem item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
}