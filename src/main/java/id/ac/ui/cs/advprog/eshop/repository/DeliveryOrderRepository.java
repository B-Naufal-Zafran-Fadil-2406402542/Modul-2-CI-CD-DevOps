package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.DeliveryOrder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DeliveryOrderRepository {
    private Map<String, DeliveryOrder> database = new HashMap<>();

    public void save(DeliveryOrder order) {
        database.put(order.getId(), order);
        System.out.println("Order " + order.getId() + " saved to DB.");
    }

    public DeliveryOrder findById(String id) {
        return database.get(id);
    }
}
