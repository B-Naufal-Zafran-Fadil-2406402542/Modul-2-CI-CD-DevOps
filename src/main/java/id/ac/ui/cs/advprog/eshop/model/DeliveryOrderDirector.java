package id.ac.ui.cs.advprog.eshop.model;

public class DeliveryOrderDirector {
    public void buildBaseDeliveryOrder(DeliveryOrderBuilder builder) {
        builder.reset();
        builder.setDestination("");
    }
}
