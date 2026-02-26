package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {
    @Test
    void testCarFields() {
    Car car = new Car();
    car.setId("eb558e11-1c31-42a4-aa4b-1446511221f5");
    car.setName("Toyota");
    car.setCarColor("Red");
    car.setQuantity(10);

    assertEquals("eb558e11-1c31-42a4-aa4b-1446511221f5", car.getId());
    assertEquals("Toyota", car.getName());
    assertEquals("Red", car.getCarColor());
    assertEquals(10, car.getQuantity());
    }
}
