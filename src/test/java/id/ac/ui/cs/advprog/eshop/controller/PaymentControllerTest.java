package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
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

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    void testPaymentDetailSearchPage() throws Exception {
        mockMvc.perform(get("/payment/detail"))
                .andExpect(status().isOk())
                .andExpect(view().name("PaymentDetailSearch"));
    }

    @Test
    void testPaymentDetailPage() throws Exception {
        Order order = new Order("id", new ArrayList<Product>(){{add(new Product());}}, 1L, "author");
        Payment payment = new Payment("pid", order, "VOUCHER_CODE", new HashMap<>());
        when(paymentService.getPayment("pid")).thenReturn(payment);

        mockMvc.perform(get("/payment/detail/pid"))
                .andExpect(status().isOk())
                .andExpect(view().name("PaymentDetail"))
                .andExpect(model().attribute("payment", payment));
    }

    @Test
    void testPaymentList() throws Exception {
        List<Payment> payments = new ArrayList<>();
        when(paymentService.getAllPayments()).thenReturn(payments);

        mockMvc.perform(get("/payment/admin/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("PaymentAdminList"))
                .andExpect(model().attribute("payments", payments));
    }

    @Test
    void testAdminPaymentDetail() throws Exception {
        Order order = new Order("id", new ArrayList<Product>(){{add(new Product());}}, 1L, "author");
        Payment payment = new Payment("pid", order, "VOUCHER_CODE", new HashMap<>());
        when(paymentService.getPayment("pid")).thenReturn(payment);

        mockMvc.perform(get("/payment/admin/detail/pid"))
                .andExpect(status().isOk())
                .andExpect(view().name("PaymentAdminDetail"))
                .andExpect(model().attribute("payment", payment));
    }

    @Test
    void testSetStatus() throws Exception {
        Order order = new Order("id", new ArrayList<Product>(){{add(new Product());}}, 1L, "author");
        Payment payment = new Payment("pid", order, "VOUCHER_CODE", new HashMap<>());
        when(paymentService.getPayment("pid")).thenReturn(payment);

        mockMvc.perform(post("/payment/admin/set-status/pid").param("status", "SUCCESS"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/payment/admin/list"));
    }
}
