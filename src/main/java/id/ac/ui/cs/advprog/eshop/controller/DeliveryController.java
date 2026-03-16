package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.DeliveryOrder;
import id.ac.ui.cs.advprog.eshop.model.DeliveryOrderBuilder;
import id.ac.ui.cs.advprog.eshop.service.DeliveryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeliveryController {
    private final DeliveryService service;

    public DeliveryController(DeliveryService service) {
        this.service = service;
    }

    @PostMapping("/delivery-order")
    public String createOrder(@RequestBody DeliveryOrder order) {
        // Notice: The client has to send a massive object with 20 fields
        // even if most are optional/null.

        DeliveryOrderBuilder builder;
//        builder.build(order);
        service.processOrder(order);
        return "Order Processed";
    }
}