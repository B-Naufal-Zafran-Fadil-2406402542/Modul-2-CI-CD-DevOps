package id.ac.ui.cs.advprog.eshop.model;

import enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    private List<Product> products;

    @BeforeEach
    void setup() {
        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-4609-8860-71af6af63bd6");
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(2);
        Product product2 = new Product();
        product2.setId("a2c62328-4837-4664-83c7-f32db8620155");
        product2.setName("Sabun Cap Usep");
        product2.setQuantity(1);
        this.products.add(product1);
        this.products.add(product2);
    }

    @Test
    void testOrderBuilder() {
        Order order = Order.builder()
                .id("13652556-812a-4c07-6546-54eb1396d79b")
                .products(this.products)
                .orderTime(1708560800L)
                .author("Safira Sudrajat")
                .status(OrderStatus.WAITING_PAYMENT.getValue())
                .build();

        assertEquals("13652556-812a-4c07-6546-54eb1396d79b", order.getId());
        assertSame(this.products, order.getProducts());
        assertEquals(1708560800L, order.getOrderTime());
        assertEquals("Safira Sudrajat", order.getAuthor());
        assertEquals("WAITING_PAYMENT", order.getStatus());
    }

    @Test
    void testCreateOrderEmptyProduct() {
        this.products.clear();
        assertThrows(IllegalArgumentException.class, () -> {
            Order order = new Order("13652556-0128-4c07-b546-54eb1396d79b",
                    this.products, 1708560688L, "Safira Sudrajat");
        });
    }

    @Test
    void testCreateOrderDefaultStatus() {
        Order order = new Order("13652556-812a-4c07-6546-54eb1396d79b",
                this.products, 1708560800L, "Safira Sudrajat");
        assertSame(this.products, order.getProducts());
        assertEquals(2, order.getProducts().size());
        assertEquals("Sampo Cap Bambang", order.getProducts().get(0).getName());
        assertEquals("Sabun Cap Usep", order.getProducts().get(1).getName());
        assertEquals("13652556-812a-4c07-6546-54eb1396d79b", order.getId());
        assertEquals(1708560800L, order.getOrderTime());
        assertEquals("Safira Sudrajat", order.getAuthor());
        assertEquals("WAITING_PAYMENT", order.getStatus());
    }

    @Test
    void testCreateOrderSuccessStatus() {
        Order order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                this.products, 1708568000L, "Safira Sudrajat", OrderStatus.SUCCESS.getValue());
        assertEquals("SUCCESS", order.getStatus());
    }

    @Test
    void testCreateOrderInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            Order order = new Order("13652556-0128-4c07-6546-54eb1396d79b",
                    this.products, 1708568000L, "Safira Sudrajat", "MEOW");
        });
    }

    @Test
    void testSetStatusToCancelled() {
        Order order = new Order("13652556-0128-4c07-6546-54eb1396d79b",
                this.products, 1708560000L, "Safira Sudrajat");
        order.setStatus("CANCELLED");
        assertEquals("CANCELLED", order.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus() {
        Order order = new Order("13652556-012a-4c87-6546-54eb1396d79b",
                this.products, 1768560000L, "Safira Sudrajat");
        assertThrows(IllegalArgumentException.class, () -> order.setStatus("MEOW"));
    }
}