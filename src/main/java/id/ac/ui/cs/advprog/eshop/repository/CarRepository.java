package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.model.Item;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class CarRepository extends ItemRepository<Car> {

    @Override
    public void updateItemData(Car updatedCar) {
        Car car = getItemById(updatedCar.getId());
        car.setName(updatedCar.getName());
        car.setCarColor(updatedCar.getCarColor());
        car.setQuantity(updatedCar.getQuantity());
    }
}