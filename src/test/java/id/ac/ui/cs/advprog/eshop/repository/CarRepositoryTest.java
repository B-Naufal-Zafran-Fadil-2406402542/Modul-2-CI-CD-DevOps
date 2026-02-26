package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.exception.ItemNotFoundException;
import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {

    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testUpdateItemDataSuccess() {
        // Arrange
        Car car = new Car();
        car.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        car.setName("Toyota");
        car.setCarColor("Red");
        car.setQuantity(100);
        carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedCar.setName("Toyota Updated");
        updatedCar.setCarColor("Blue");
        updatedCar.setQuantity(50);

        // Act
        carRepository.updateItemData(updatedCar);

        // Assert
        Car result = carRepository.getItemById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals("Toyota Updated", result.getName());
        assertEquals("Blue", result.getCarColor());
        assertEquals(50, result.getQuantity());
    }

    @Test
    void testUpdateItemDataNotFound() {
        Car updatedCar = new Car();
        updatedCar.setId("non-existent");
        
        assertThrows(ItemNotFoundException.class, () -> carRepository.updateItemData(updatedCar));
    }

    @Test
    void testFindAll() {
        Car car1 = new Car();
        car1.setId("1");
        carRepository.create(car1);
        
        Car car2 = new Car();
        car2.setId("2");
        carRepository.create(car2);
        
        int count = 0;
        var iterator = carRepository.findAll();
        while(iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertEquals(2, count);
    }

    @Test
    void testDeleteItemById() {
        Car car = new Car();
        car.setId("test-id");
        carRepository.create(car);
        
        carRepository.deleteItemById("test-id");
        
        assertThrows(ItemNotFoundException.class, () -> carRepository.getItemById("test-id"));
    }
}
