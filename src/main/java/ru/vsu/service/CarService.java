package ru.vsu.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.vsu.dao.DAO;
import ru.vsu.dao.SoldCarDAO;
import ru.vsu.data.entity.Car;
import ru.vsu.data.entity.Customer;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@Builder
public class CarService {
    private DAO<Car> carDAO;
    private DAO<Car> soldCarDAO;
    private CustomerService customerService;

    public List<Car> getAllCars() {
        return carDAO.getAll();
    }

    public List<Car> getAllSoldCar() {
        return soldCarDAO.getAll();
    }

    public void
    sellCar(Car car, Customer customer) {
        deleteCar(car);
        customerService.createCustomer(customer);
        car.setCustomer(customer);
        soldCarDAO.create(car);
    }

    public Car getCar(int id) {
        return carDAO.get(id).orElseThrow();
    }

    public void createCar(Car car) {
        carDAO.create(car);
    }

    public void editCar(int carId, Car car) {
        car.setId(carId);
        carDAO.update(car);
    }

    public void deleteCar(Car car) {
        carDAO.delete(car);
    }
}
