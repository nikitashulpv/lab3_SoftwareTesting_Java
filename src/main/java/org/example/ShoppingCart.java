package org.example;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ShoppingCart {
    private final Map<String, Product> catalog;
    private final Map<String, Integer> items;
    private int discount = 0;

    public ShoppingCart(Map<String, Product> catalog) {
        this.catalog = catalog;
        this.items = new HashMap<>();
    }

    public void addProduct(String id, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException ("Количество товара должно быть положительным");
        }
        if (!catalog.containsKey(id)) {
            throw new IllegalArgumentException("Товар '" + id + "' не найден в каталоге");
        }

        Product product = catalog.get(id);
        int currentInCart = items.getOrDefault(id, 0);

        if (currentInCart + quantity > product.getStock()) {
            throw new IllegalArgumentException(
                    "Недостаточно товара '" + id + "' на складе: " +
                            "доступно " + product.getStock() + ", " +
                            "в корзине уже " + currentInCart + ", " +
                            "запрошено ещё " + quantity
            );
        }

        items.put(id, currentInCart + quantity);
    }

    public void removeProduct(String id, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException ("Количество товара должно быть положительным");
        }

        if (!items.containsKey(id)) {
            throw new IllegalArgumentException ("Товар не найден в корзине");
        }

        if (quantity > items.get(id)) {
            throw new IllegalArgumentException ("Недостаточно товара в корзине");
        }

        if (quantity == items.get(id)) {
            items.remove(id);
        }
        else {
            items.put(id, items.get(id) - quantity);
        }
    }

    public void applyPromo(int percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException ("Скидка должна быть в диапазоне 0..100");
        }
        discount = percent;
    }

    public double totalSum() {
        double total = 0;
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            Product product = catalog.get(productId);
            total += product.getPrice() * quantity;
        }
        return total * (1 - discount / 100.0);
    }
}
