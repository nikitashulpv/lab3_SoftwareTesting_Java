package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testCreateValidProduct() {
        Product p = new Product("prod_1", "Смартфон", 29999, 10);
        assertEquals("prod_1", p.getId());
        assertEquals("Смартфон", p.getName());
        assertEquals(29999, p.getPrice(), 0.001);
        assertEquals(10, p.getStock());
    }

    @Test
    void testNegativePriceRaises() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product("prod_1", "X", -1, 10)
        );
    }

    @Test
    void testNegativeStockRaises() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product("prod_1", "X", 100, -1)
        );
    }

    @Test
    void testIsAvailableTrue() {
        Product p = new Product("prod_1", "X", 100, 5);
        assertTrue(p.isAvailable());
    }

    @Test
    void testIsAvailableFalse() {
        Product p = new Product("prod_1", "X", 100, 0);
        assertFalse(p.isAvailable());
    }

    @Test
    void testReduceStockSuccess() {
        Product p = new Product("prod_1", "X", 100, 10);
        p.reduceStock(3);
        assertEquals(7, p.getStock());
    }

    @Test
    void testReduceStockTooMuchRaises() {
        Product p = new Product("prod_1", "X", 100, 5);
        assertThrows(IllegalArgumentException.class, () ->
                p.reduceStock(10)
        );
    }

    @Test
    void testToString() {
        Product p = new Product("prod_1", "Смартфон", 29999, 10);
        String s = p.toString();
        assertTrue(s.contains("prod_1"));
        assertTrue(s.contains("Смартфон"));
    }
}