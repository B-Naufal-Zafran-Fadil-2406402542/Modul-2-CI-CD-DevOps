package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    /**
     * Shows the page to create a new order.
     */
    @GetMapping("/create")
    public String createOrderPage(Model model) {
        return "CreateOrder";
    }

    /**
     * Handles the creation of a new order for a given author.
     */
    @PostMapping("/create")
    public String createOrder(@RequestParam String author) {
        List<Product> products = productService.findAll();
        if (products.isEmpty()) {
            return "redirect:/product/list";
        }
        Order order = new Order(UUID.randomUUID().toString(), products, System.currentTimeMillis(), author);
        orderService.createOrder(order);
        return "redirect:/order/history/" + author;
    }

    /**
     * Shows the order history search page.
     */
    @GetMapping("/history")
    public String historyPage() {
        return "OrderHistory";
    }

    /**
     * Processes history search request and shows results.
     */
    @PostMapping("/history")
    public String searchHistory(@RequestParam String author, Model model) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("orders", orders);
        model.addAttribute("author", author);
        return "OrderList";
    }

    /**
     * Shows the list of orders for a specific author.
     */
    @GetMapping("/history/{author}")
    public String orderList(@PathVariable String author, Model model) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("orders", orders);
        model.addAttribute("author", author);
        return "OrderList";
    }

    /**
     * Shows the payment page for a specific order.
     */
    @GetMapping("/pay/{orderId}")
    public String payOrderPage(@PathVariable String orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "PayOrder";
    }

    /**
     * Handles order payment request.
     */
    @PostMapping("/pay/{orderId}")
    public String payOrder(@PathVariable String orderId, @RequestParam String method, @RequestParam Map<String, String> allParams, Model model) {
        Order order = orderService.findById(orderId);
        Map<String, String> paymentData = new HashMap<>(allParams);
        paymentData.remove("method");
        
        Payment payment = paymentService.addPayment(order, method, paymentData);
        paymentService.setStatus(payment, payment.getStatus());
        model.addAttribute("payment", payment);
        return "PaymentResult";
    }
}
