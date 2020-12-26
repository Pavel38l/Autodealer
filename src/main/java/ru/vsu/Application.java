package ru.vsu;


import ru.vsu.dao.*;
import ru.vsu.data.entity.Car;
import ru.vsu.data.entity.Customer;
import ru.vsu.service.CarService;
import ru.vsu.service.CustomerService;
import ru.vsu.ui.CarMenu;
import ru.vsu.ui.CustomerMenu;
import ru.vsu.ui.Printer;
import ru.vsu.ui.ViewMenu;

import java.sql.Date;
import java.util.List;

public class Application {
    public void run() {
//        DAO<Customer> customerDAO = new CustomerDao(new SimpleConnectionBuilder());
//        DAO<Car> carDAO = new CarDAO(new SimpleConnectionBuilder());
//        Customer c = new Customer(3, "Anton", Date.valueOf("2001-01-01"), "male");
//        //customerDAO.create(c);
//        //carDAO.create(new Car(-1, "Lada", "Vesta", c));
//        carDAO.update(new Car(2, "Light", "Lada", "Granta", c));
//        List<Car> cars = carDAO.getAll();
//        cars.forEach(System.out::println);



        ConnectionBuilder connectionBuilder = new SimpleConnectionBuilder();
        DAO<Customer> customerDAO = new CustomerDao(connectionBuilder);
        DAO<Car> carDAO = new CarDAO(connectionBuilder), soldCarDAO = new SoldCarDAO(connectionBuilder, customerDAO);

        CustomerService customerService = CustomerService.builder().customerDAO(customerDAO).build();
        CarService carService = CarService.builder()
                .carDAO(carDAO)
                .customerService(customerService)
                .soldCarDAO(soldCarDAO).build();


        CarMenu carMenu = new CarMenu(new Printer(), carService);
        CustomerMenu customerMenu = new CustomerMenu(new Printer(), customerService, carService, carMenu);
        ViewMenu menu = ViewMenu.builder()
                .carMenu(carMenu)
                .customerMenu(customerMenu)
                .carService(carService)
                .customerService(customerService)
                .printer(new Printer())
                .build();
        menu.show();
    }
}
