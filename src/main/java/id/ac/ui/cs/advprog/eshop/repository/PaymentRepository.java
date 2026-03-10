package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepository {
    private List<Payment> paymentData = new ArrayList<>();

    /**
     * Saves a payment object to the repository.
     * @param payment the payment to save
     * @return the saved payment
     */
    public Payment save(Payment payment) {
        int i = 0;
        for (Payment savedPayment : paymentData) {
            if (savedPayment.getId().equals(payment.getId())) {
                paymentData.set(i, payment);
                return payment;
            }
            i++;
        }
        paymentData.add(payment);
        return payment;
    }

    /**
     * Finds a payment by its unique ID.
     * @param id the payment ID
     * @return the found payment or null if not found
     */
    public Payment findById(String id) {
        for (Payment savedPayment : paymentData) {
            if (savedPayment.getId().equals(id)) {
                return savedPayment;
            }
        }
        return null;
    }

    /**
     * Returns a list of all saved payments.
     * @return list of all payments
     */
    public List<Payment> findAll() {
        return new ArrayList<>(paymentData);
    }
}
