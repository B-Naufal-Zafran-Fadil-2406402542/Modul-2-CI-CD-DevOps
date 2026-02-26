package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.model.Product;

import java.util.List;

public interface CarService extends ItemService<Car>{
    public Car create(Car car);
    public List<Car> findAll();
    public Car getItemById(String carId);
    public void update(Car car);
    public void deleteById(String carId);
}