package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    private Map<String, Product> catalog;
    private ShoppingCart cart;

    @BeforeEach
    void setUp() {
        catalog = new HashMap<>();
        catalog.put("prod_1", new Product("prod_1", "Смартфон", 29999, 10));
        catalog.put("prod_2", new Product("prod_2", "Наушники", 3000, 5));
        cart = new ShoppingCart(catalog);
    }

    @Test
    void testApplyPromo() {
        cart.addProduct("prod_1", 1);
        cart.applyPromo(10);
        assertEquals(26999.1, cart.totalSum(), 0.001);
    }

    @Test
    void testPromoNoDiscount() {
        cart.addProduct("prod_1", 1);
        cart.applyPromo(0);
        assertEquals(29999, cart.totalSum(), 0.001);
    }

    @Test
    void testPromoDiscountLess0() {
        cart.addProduct("prod_1", 1);
        assertThrows(IllegalArgumentException.class, () ->
                cart.applyPromo(-1)
        );
    }

    @Test
    void testPromoDiscountOver100() {
        cart.addProduct("prod_1", 1);
        assertThrows(IllegalArgumentException.class, () ->
                cart.applyPromo(101)
        );
    }

    @Test
    void testPromoFullDiscount() {
        cart.addProduct("prod_1", 1);
        cart.applyPromo(100);
        assertEquals(0, cart.totalSum(), 0.001);
    }

    @Test
    void testPromoOnEmptyCart() {
        cart.applyPromo(10);
        assertEquals(0, cart.totalSum(), 0.001);
    }

    @Test
    void testPromoMultipleProducts() {
        cart.addProduct("prod_1", 2);
        cart.addProduct("prod_2", 3);
        cart.applyPromo(10);
        assertEquals((59998 + 9000) * 0.9, cart.totalSum(), 0.001);
    }

    @Test
    void testAddNewProduct() {
        cart.addProduct("prod_1", 3);
        assertEquals(3, cart.getItems().get("prod_1"));
    }

    @Test
    void testAddExistingProductSums() {
        cart.addProduct("prod_1", 3);
        cart.addProduct("prod_1", 2);
        assertEquals(5, cart.getItems().get("prod_1"));
    }

    @Test
    void testAddZeroRaises() {
        assertThrows(IllegalArgumentException.class, () ->
                cart.addProduct("prod_1", 0)
        );
    }

    @Test
    void testAddNegativeRaises() {
        assertThrows(IllegalArgumentException.class, () ->
                cart.addProduct("prod_1", -1)
        );
    }

    @Test
    void testAddUnknownProductRaises() {
        assertThrows(IllegalArgumentException.class, () ->
                cart.addProduct("nonexistent", 1)
        );
    }

    @Test
    void testAddExceedingStockRaises() {
        assertThrows(IllegalArgumentException.class, () ->
                cart.addProduct("prod_1", 11)
        );
    }

    @Test
    void testAddExceedingStockWithExisting() {
        cart.addProduct("prod_1", 8);
        assertThrows(IllegalArgumentException.class, () ->
                cart.addProduct("prod_1", 5)
        );
    }

    @Test
    void testDeletePartial() {
        cart.addProduct("prod_1", 5);
        cart.removeProduct("prod_1", 2);
        assertEquals(3, cart.getItems().get("prod_1"));
    }

    @Test
    void testDeleteFull() {
        cart.addProduct("prod_1", 5);
        cart.removeProduct("prod_1", 5);
        assertFalse(cart.getItems().containsKey("prod_1"));
    }

    @Test
    void testDeleteZeroRaises() {
        cart.addProduct("prod_1", 5);
        assertThrows(IllegalArgumentException.class, () ->
                cart.removeProduct("prod_1", 0)
        );
    }

    @Test
    void testDeleteUnknownRaises() {
        assertThrows(IllegalArgumentException.class, () ->
                cart.removeProduct("prod_1", 1)
        );
    }

    @Test
    void testDeleteTooMuchRaises() {
        cart.addProduct("prod_1", 3);
        assertThrows(IllegalArgumentException.class, () ->
                cart.removeProduct("prod_1", 5)
        );
    }

    @Test
    void testTotalEmptyCart() {
        assertEquals(0, cart.totalSum(), 0.001);
    }

    @Test
    void testTotalOneProduct() {
        cart.addProduct("prod_1", 2);
        assertEquals(59998, cart.totalSum(), 0.001);
    }

    @Test
    void testTotalMultipleProducts() {
        cart.addProduct("prod_1", 2);
        cart.addProduct("prod_2", 3);
        assertEquals(68998, cart.totalSum(), 0.001);
    }
}