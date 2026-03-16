package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.DeliveryOrder;
import id.ac.ui.cs.advprog.eshop.repository.DeliveryOrderRepository;
import org.springframework.stereotype.Service;

import java.awt.image.Kernel;

interface TypeStrategy {
    double calculateCost(double weight);
}

class StandardTypeStrategy implements  TypeStrategy{
    @Override
    public double calculateCost(double weight) {
        return weight*5.0;
    }
}

class ExpressTypeStrategy implements  TypeStrategy{
    @Override
    public double calculateCost(double weight) {
        return weight*10.0+20.0;
    }
}

class DroneTypeStrategy implements  TypeStrategy{
    @Override
    public double calculateCost(double weight) {
        return weight*15.0+50;
    }
}

class TypeContext {
    TypeStrategy typeStrategy;

    public TypeContext(TypeStrategy typeStrategy) {
        this.typeStrategy = typeStrategy;
    }

    public double execute(double weight) {
        return typeStrategy.calculateCost(weight);
    }
}

// 1. The Interface/Abstract Class
abstract class StatusState {
    // We pass the Order so the state can change the Order's status if needed
    public abstract StatusState nextStatus();
    public abstract void handleNotification();
}

// 2. Concrete States
class PendingStatusState extends StatusState {
    @Override
    public StatusState nextStatus() { return new AssignedStatusState(); }

    @Override
    public void handleNotification() { System.out.println("Notify Warehouse: Order Assigned."); }
}

class AssignedStatusState extends StatusState {
    @Override
    public StatusState nextStatus() { return new InTransitStatusState(); }

    @Override
    public void handleNotification() { System.out.println("Notify Customer: Order Shipped."); }
}

class InTransitStatusState extends StatusState {
    @Override
    public StatusState nextStatus() { return new InTransitStatusState(); }

    @Override
    public void handleNotification() { System.out.println("Notify Customer: Order Shipped."); }
}

// 3. The Context (Your DeliveryOrder class can act as this)
class OrderStatusContext {
    private StatusState currentState;

    public OrderStatusContext() {
        this.currentState = new PendingStatusState(); // Initial State
    }

    public OrderStatusContext(StatusState state) {
        this.currentState = state;
    }

    public void transition() {
        // Trigger notification logic first
        currentState.handleNotification();
        // Then move to next state
        this.currentState = currentState.nextStatus();
    }
}

@Service
public class DeliveryService {
    private final DeliveryOrderRepository repository;

    public DeliveryService(DeliveryOrderRepository repository) {
        this.repository = repository;
    }

    public void processOrder(DeliveryOrder order) {
        // LOGIC 1: Calculating Shipping Cost
        double cost = 0;
        TypeStrategy typeStategy = new StandardTypeStrategy();
        if (order.getType().equals("STANDARD")) {
            typeStategy = new StandardTypeStrategy();
        } else if (order.getType().equals("EXPRESS")) {
            typeStategy = new ExpressTypeStrategy() ;
        } else if (order.getType().equals("DRONE")) {
            typeStategy = new DroneTypeStrategy();
        }

        cost = new TypeContext(typeStategy).execute(order.getWeight());
        System.out.println("Calculated cost: " + cost);

        // LOGIC 2: Status Transitions
        OrderStatusContext statusContext;
        if (order.getStatus().equals("PENDING")) {
            statusContext = new OrderStatusContext(new PendingStatusState());
            statusContext.transition();
            System.out.println("Notify Warehouse: Order Assigned.");
        } else if (order.getStatus().equals("ASSIGNED")) {
//            statusContext.setStatus("IN_TRANSIT");
            System.out.println("Notify Customer: Order Shipped.");
        }



        // LOGIC 3: Legacy Third-Party Integration
        // We must use "GlobalLogisticsAPI" which uses a completely different naming convention
        GlobalLogisticsAPI legacyApi = new GlobalLogisticsAPI();
        legacyApi.shipOrderV2(order.getDestination(), order.getId(), "PRIORITY_HIGH");

        repository.save(order);
    }
}

// Inaccessible 3rd Party Class
class GlobalLogisticsAPI {
    public void shipOrderV2(String dest, String trackingCode, String priority) {
        System.out.println("Legacy API: Shipping to " + dest + " with code " + trackingCode);
    }
}
