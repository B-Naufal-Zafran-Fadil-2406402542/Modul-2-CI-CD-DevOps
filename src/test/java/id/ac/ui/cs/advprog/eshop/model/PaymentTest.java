package id.ac.ui.cs.advprog.eshop.model;

import enums.OrderStatus;
import enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    private List<Product> products;
    private Order order;

    @BeforeEach
    void setup() {
        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-4609-8860-71af6af63bd6");
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(2);
        this.products.add(product1);

        this.order = new Order("13652556-812a-4c07-6546-54eb1396d79b",
                this.products, 1708560800L, "Safira Sudrajat");
    }

    @Test
    void testCreatePaymentVoucherSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("13652556-812a-4c07-6546-54eb1396d79b",
                this.order, "VOUCHER_CODE", paymentData);

        assertEquals("13652556-812a-4c07-6546-54eb1396d79b", payment.getId());
        assertEquals(this.order, payment.getOrder());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherRejectedInvalidLength() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC567"); // 15 chars
        Payment payment = new Payment("13652556-812a-4c07-6546-54eb1396d79b",
                this.order, "VOUCHER_CODE", paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherRejectedNotStartWithESHOP() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "MYSHOP1234ABC5678"); // 16 chars
        Payment payment = new Payment("13652556-812a-4c07-6546-54eb1396d79b",
                this.order, "VOUCHER_CODE", paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherRejectedLessNumerical() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC567A"); // 7 numerical
        Payment payment = new Payment("13652556-812a-4c07-6546-54eb1396d79b",
                this.order, "VOUCHER_CODE", paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherRejectedNullVoucherCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", null);
        Payment payment = new Payment("13652556-812a-4c07-6546-54eb1396d79b",
                this.order, "VOUCHER_CODE", paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherRejectedMoreNumerical() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234567890AB"); // 10 numerical
        // Wait, ESHOP (5) + 1234567890 (10) + AB (2) = 17 chars.
        // I need exactly 16 chars.
        paymentData.put("voucherCode", "ESHOP1234567890A"); // 10 numerical, 5 + 10 + 1 = 16 chars.
        Payment payment = new Payment("13652556-812a-4c07-6546-54eb1396d79b",
                this.order, "VOUCHER_CODE", paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "12345678");
        Payment payment = new Payment("13652556-812a-4c07-6546-54eb1396d79b",
                this.order, "BANK_TRANSFER", paymentData);

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferRejectedEmptyBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "12345678");
        Payment payment = new Payment("13652556-812a-4c07-6546-54eb1396d79b",
                this.order, "BANK_TRANSFER", paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferRejectedNullBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", null);
        paymentData.put("referenceCode", "12345678");
        Payment payment = new Payment("13652556-812a-4c07-6546-54eb1396d79b",
                this.order, "BANK_TRANSFER", paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferRejectedEmptyReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "");
        Payment payment = new Payment("13652556-812a-4c07-6546-54eb1396d79b",
                this.order, "BANK_TRANSFER", paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferRejectedNullReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", null);
        Payment payment = new Payment("13652556-812a-4c07-6546-54eb1396d79b",
                this.order, "BANK_TRANSFER", paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentInvalidMethod() {
        Map<String, String> paymentData = new HashMap<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("13652556-812a-4c07-6546-54eb1396d79b",
                    this.order, "INVALID", paymentData);
        });
    }

    @Test
    void testSetStatusSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("13652556-812a-4c07-6546-54eb1396d79b",
                this.order, "VOUCHER_CODE", paymentData);
        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("13652556-812a-4c07-6546-54eb1396d79b",
                this.order, "VOUCHER_CODE", paymentData);
        payment.setStatus(PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusInvalid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("13652556-812a-4c07-6546-54eb1396d79b",
                this.order, "VOUCHER_CODE", paymentData);
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("MEOW"));
    }
}
