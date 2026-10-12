package org.example;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Product {
    private final String id;
    private final String name;
    private float price;
    private int stock;

    public Product(String id, String name, float price, int stock) {
        if (price < 0) {
            throw new IllegalArgumentException("Цена не может быть отрицательной");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("Количество на складе не может быть отрицательным");
        }
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public boolean isAvailable() {
        return stock > 0;
    }

    public void reduceStock(int amount) {
        if (amount > stock) {
            throw new IllegalArgumentException("Недостаточно товара на складе");
        }
        stock -= amount;
    }
}


