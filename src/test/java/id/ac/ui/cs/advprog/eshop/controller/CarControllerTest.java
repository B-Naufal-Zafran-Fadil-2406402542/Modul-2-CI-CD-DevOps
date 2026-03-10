package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.exception.ItemNotFoundException;
import id.ac.ui.cs.advprog.eshop.model.Car;
//import id.ac.ui.cs.advprog.eshop.service.CarService;
import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarServiceImpl service;

    @Test
    void testCarListPage() throws Exception {
        List<Car> cars = new ArrayList<>();
        when(service.findAll()).thenReturn(cars);

        mockMvc.perform(get("/car/listCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("CarList"))
                .andExpect(model().attribute("cars", cars));
    }

    @Test
    void testCreateCarPage() throws Exception {
        mockMvc.perform(get("/car/createCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateCar"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void testCreateCarPost() throws Exception {
        Car car = new Car();
        mockMvc.perform(post("/car/createCar").flashAttr("car", car))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));
    }

    @Test
    void testCreateCarPostWithId() throws Exception {
        Car car = new Car();
        car.setId("existing-id");
        mockMvc.perform(post("/car/createCar").flashAttr("car", car))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));
    }

    @Test
    void testEditCarPage() throws Exception {
        Car car = new Car();
        car.setId("test-id");
        when(service.getItemById("test-id")).thenReturn(car);

        mockMvc.perform(get("/car/editCar/test-id"))
                .andExpect(status().isOk())
                .andExpect(view().name("EditCar"))
                .andExpect(model().attribute("car", car));
    }

    @Test
    void testEditCarPageNotFound() throws Exception {
        Car car = new Car(); // Just an empty car
        when(service.getItemById("test-id")).thenReturn(car);

        mockMvc.perform(get("/car/editCar/test-id"))
                .andExpect(status().isOk())
                .andExpect(view().name("EditCar"))
                .andExpect(model().attribute("car", car));
    }

    @Test
    void testEditCarPost() throws Exception {
        Car car = new Car();
        mockMvc.perform(post("/car/editCar").flashAttr("car", car))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));
    }

    @Test
    void testDeleteCar() throws Exception {
        mockMvc.perform(post("/car/deleteCar/test-id"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car/listCar"));
    }

    @Test
    void testDeleteCarNotFound() throws Exception {
        mockMvc.perform(post("/car/deleteCar/test-id"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car/listCar"));
    }
}
