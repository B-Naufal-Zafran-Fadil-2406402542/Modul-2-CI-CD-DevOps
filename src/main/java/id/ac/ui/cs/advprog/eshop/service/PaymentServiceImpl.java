package id.ac.ui.cs.advprog.eshop.service;

import enums.OrderStatus;
import enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Creates and saves a new payment for the given order.
     * @param order the order to pay for
     * @param method the payment method
     * @param paymentData specific data for the payment method
     * @return the newly created payment
     */
    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        Payment payment = new Payment(UUID.randomUUID().toString(), order, method, paymentData);
        return paymentRepository.save(payment);
    }

    /**
     * Updates the status of a payment and its related order.
     * @param payment the payment to update
     * @param status the new status
     * @return the updated payment
     */
    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);
        if (status.equals(PaymentStatus.SUCCESS.getValue())) {
            payment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
        } else {
            payment.getOrder().setStatus(OrderStatus.FAILED.getValue());
        }
        orderRepository.save(payment.getOrder());
        return paymentRepository.save(payment);
    }

    /**
     * Retrieves a payment by its ID.
     * @param paymentId the payment ID
     * @return the found payment or null
     */
    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    /**
     * Retrieves all payments in the system.
     * @return list of all payments
     */
    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
