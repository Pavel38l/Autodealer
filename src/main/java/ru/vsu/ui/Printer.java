package ru.vsu.ui;

import ru.vsu.data.entity.Car;
import ru.vsu.data.entity.Customer;

import java.util.List;

public class Printer<T> {
    public void printCollection(List<T> list) {
        int i = 0;
        for (T obj : list) {
            System.out.println(String.format("%d. %s",  i++, obj.toString()));
        }
    }

    public void printCars(List<Car> cars) {
        int i = 1;
        for (Car c : cars) {
            System.out.println(String.format("%d. %s", i++, printCar(c)));
        }
    }

    public void printCustomers(List<Customer> customers) {
        int i = 1;
        for (Customer c : customers) {
            System.out.println(String.format("%d. %s", i++, printCustomer(c)));
        }
    }

    public void printSoldCars(List<Car> cars) {
        int i = 1;
        for (Car c : cars) {
            System.out.println(String.format("%d. %s", i, printSoldCar(c)));
        }
    }

    public String printSoldCar(Car car) {
        return printCar(car) + "   Owner:  " + printCustomer(car.getCustomer());
    }

    public String printCar(Car car) {
        return String.format("Id: %d, Type: %s, Brand: %s, Model: %s",
                car.getId(), car.getCarType(), car.getBrand(), car.getModel());
    }

    public String printCustomer(Customer customer) {
        return String.format("Id: %d, Full name: %s, Date of birth: %s, Sex: %s",
                customer.getId(), customer.getFullName(), customer.getDateOfBirth().toString(), customer.getSex());
    }

}
