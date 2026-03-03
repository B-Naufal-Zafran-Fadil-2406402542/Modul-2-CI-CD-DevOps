package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    /**
     * Shows the payment detail search page.
     */
    @GetMapping("/detail")
    public String paymentDetailSearchPage() {
        return "PaymentDetailSearch";
    }

    /**
     * Shows the details of a specific payment.
     */
    @GetMapping("/detail/{paymentId}")
    public String paymentDetailPage(@PathVariable String paymentId, Model model) {
        Payment payment = paymentService.getPayment(paymentId);
        model.addAttribute("payment", payment);
        return "PaymentDetail";
    }

    /**
     * Shows all payments for admin.
     */
    @GetMapping("/admin/list")
    public String paymentList(Model model) {
        List<Payment> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        return "PaymentAdminList";
    }

    /**
     * Shows detailed payment information for admin management.
     */
    @GetMapping("/admin/detail/{paymentId}")
    public String adminPaymentDetail(@PathVariable String paymentId, Model model) {
        Payment payment = paymentService.getPayment(paymentId);
        model.addAttribute("payment", payment);
        return "PaymentAdminDetail";
    }

    /**
     * Handles payment status update by admin.
     */
    @PostMapping("/admin/set-status/{paymentId}")
    public String setStatus(@PathVariable String paymentId, @RequestParam String status) {
        Payment payment = paymentService.getPayment(paymentId);
        paymentService.setStatus(payment, status);
        return "redirect:/payment/admin/list";
    }
}
