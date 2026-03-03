package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ProductService productService;

    @MockBean
    private PaymentService paymentService;

    @Test
    void testCreateOrderPage() throws Exception {
        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateOrder"));
    }

    @Test
    void testCreateOrderPost() throws Exception {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        products.add(product);
        when(productService.findAll()).thenReturn(products);

        mockMvc.perform(post("/order/create").param("author", "Safira"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/history/Safira"));
    }

    @Test
    void testCreateOrderPostEmptyProducts() throws Exception {
        when(productService.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(post("/order/create").param("author", "Safira"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));
    }

    @Test
    void testHistoryPage() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("OrderHistory"));
    }

    @Test
    void testSearchHistory() throws Exception {
        mockMvc.perform(post("/order/history").param("author", "Safira"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/history/Safira"));
    }

    @Test
    void testOrderList() throws Exception {
        List<Order> orders = new ArrayList<>();
        when(orderService.findAllByAuthor("Safira")).thenReturn(orders);

        mockMvc.perform(get("/order/history/Safira"))
                .andExpect(status().isOk())
                .andExpect(view().name("OrderList"))
                .andExpect(model().attribute("orders", orders))
                .andExpect(model().attribute("author", "Safira"));
    }

    @Test
    void testPayOrderPage() throws Exception {
        Order order = new Order("id", new ArrayList<Product>(){{add(new Product());}}, 1L, "author");
        when(orderService.findById("id")).thenReturn(order);

        mockMvc.perform(get("/order/pay/id"))
                .andExpect(status().isOk())
                .andExpect(view().name("PayOrder"))
                .andExpect(model().attribute("order", order));
    }

    @Test
    void testPayOrderPost() throws Exception {
        Order order = new Order("id", new ArrayList<Product>(){{add(new Product());}}, 1L, "author");
        when(orderService.findById("id")).thenReturn(order);
        
        Payment payment = new Payment("pid", order, "VOUCHER_CODE", new HashMap<>());
        when(paymentService.addPayment(any(), anyString(), anyMap())).thenReturn(payment);

        mockMvc.perform(post("/order/pay/id")
                .param("method", "VOUCHER_CODE")
                .param("voucherCode", "ESHOP1234ABC5678"))
                .andExpect(status().isOk())
                .andExpect(view().name("PaymentResult"))
                .andExpect(model().attribute("payment", payment));
    }
}
