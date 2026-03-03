package id.ac.ui.cs.advprog.eshop.model;

import enums.PaymentStatus;
import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {
    private final String id;
    private final Order order;
    private final String method;
    private String status;
    private final Map<String, String> paymentData;

    public Payment(String id, Order order, String method, Map<String, String> paymentData) {
        this.id = id;
        this.order = order;
        this.method = method;
        this.paymentData = paymentData;

        if (method.equals("VOUCHER_CODE")) {
            String voucherCode = paymentData.get("voucherCode");
            if (voucherCode != null && voucherCode.length() == 16 && voucherCode.startsWith("ESHOP") && countDigits(voucherCode) == 8) {
                this.status = PaymentStatus.SUCCESS.getValue();
            } else {
                this.status = PaymentStatus.REJECTED.getValue();
            }
        } else if (method.equals("BANK_TRANSFER")) {
            String bankName = paymentData.get("bankName");
            String referenceCode = paymentData.get("referenceCode");
            if (bankName != null && !bankName.isEmpty() && referenceCode != null && !referenceCode.isEmpty()) {
                this.status = PaymentStatus.SUCCESS.getValue();
            } else {
                this.status = PaymentStatus.REJECTED.getValue();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Counts the number of numerical digits in a string.
     * @param s the string to count digits from
     * @return the number of digits found
     */
    private int countDigits(String s) {
        return (int) s.chars().filter(Character::isDigit).count();
    }
}
