package id.ac.ui.cs.advprog.eshop.service;

import enums.OrderStatus;
import enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderRepository orderRepository;

    private List<Payment> payments;
    private List<Order> orders;

    @BeforeEach
    void setup() {
        payments = new ArrayList<>();
        orders = new ArrayList<>();

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-4609-8860-71af6af63bd6");
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(2);
        products.add(product1);

        Order order = new Order("13652556-812a-4c07-6546-54eb1396d79b",
                products, 1708560800L, "Safira Sudrajat");
        orders.add(order);

        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment1 = new Payment("a2c62328-4837-4664-83c7-f32db8620155",
                order, "VOUCHER_CODE", paymentData1);
        payments.add(payment1);
    }

    @Test
    void testAddPayment() {
        Order order = orders.get(0);
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(order, "VOUCHER_CODE", paymentData);

        assertNotNull(result);
        assertEquals(order, result.getOrder());
        assertEquals("VOUCHER_CODE", result.getMethod());
        assertEquals(paymentData, result.getPaymentData());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusSuccess() {
        Payment payment = payments.get(0);
        Order order = payment.getOrder();
        
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testSetStatusRejected() {
        Payment payment = payments.get(0);
        Order order = payment.getOrder();
        
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testSetStatusInvalid() {
        Payment payment = payments.get(0);
        assertThrows(IllegalArgumentException.class, () -> paymentService.setStatus(payment, "MEOW"));
    }

    @Test
    void testGetPaymentIfFound() {
        Payment payment = payments.get(0);
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);

        Payment result = paymentService.getPayment(payment.getId());

        assertEquals(payment, result);
    }

    @Test
    void testGetPaymentIfNotFound() {
        when(paymentRepository.findById("non-existent")).thenReturn(null);

        Payment result = paymentService.getPayment("non-existent");

        assertNull(result);
    }

    @Test
    void testGetAllPayments() {
        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(payments, result);
    }
}
