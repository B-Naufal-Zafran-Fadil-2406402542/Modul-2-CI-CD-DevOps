package id.ac.ui.cs.advprog.eshop.model;

import java.util.UUID;

public class DeliveryOrderBuilder {
    private DeliveryOrder deliveryOrder;

    public DeliveryOrderBuilder() {
        reset();
    }

    public void reset () {
        this.deliveryOrder = new DeliveryOrder();
        this.deliveryOrder.setId(String.valueOf(UUID.randomUUID()));
    }

    public void setDestination(String destination) {
        this.deliveryOrder.setDestination(destination);
    }

    public void setType(String type) {
        this.deliveryOrder.setDestination(type);
    }

    public DeliveryOrder getItem() {
        return this.deliveryOrder;
    }
}
