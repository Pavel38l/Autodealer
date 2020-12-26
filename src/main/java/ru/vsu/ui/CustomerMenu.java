package ru.vsu.ui;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.vsu.data.entity.Car;
import ru.vsu.data.entity.Customer;
import ru.vsu.service.CarService;
import ru.vsu.service.CustomerService;

import java.sql.Date;
import java.util.Scanner;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CustomerMenu {
    Scanner sc = new Scanner(System.in);
    Printer printer;
    CustomerService customerService;
    CarService carService;
    CarMenu carMenu;

    public CustomerMenu(Printer printer, CustomerService customerService, CarService carService, CarMenu carMenu) {
        this.printer = printer;
        this.customerService = customerService;
        this.carService = carService;
        this.carMenu = carMenu;
    }

    public void showEdit() {
        int customerId = chooseCustomer();
        String fullName = chooseName();
        Date date = chooseDate();
        String sex = chooseSex();
        customerService.editCustomer(new Customer(customerId, fullName, date, sex));
        return;
    }

    private long chooseCar() {
        while (true) {
            System.out.println("Enter carId:");
            String answer = sc.nextLine();
            try {
                long ans = Long.parseLong(answer);
                if (ans < 0 || ans >= carService.getAllCars().size()) {
                    System.out.println("There is no such car! Try again!");
                } else {
                    return ans;
                }
            } catch (Exception e) {
                System.out.println("There is no such car! Try again!");
            }
        }
    }

    public void showSellCar() {
        int carId = carMenu.showCarMenu();
        Car car = carService.getCar(carId);
        String fullName = chooseName();
        Date date = chooseDate();
        String sex = chooseSex();
        carService.sellCar(car, new Customer(0, fullName, date, sex));
    }


    private String chooseSex() {
        System.out.println("Enter sex: ");
        return sc.nextLine().trim();
    }

//    private Customer.Sex chooseSex() {
//        System.out.println("Enter sex: ");
//        return Customer.Sex.valueOf(sc.nextLine().trim());
//    }

    private Date chooseDate() {
        while (true) {
            System.out.println("Enter customer date of birth: ");
            String answer = sc.nextLine();
            try {
                return Date.valueOf(answer);
            } catch (Exception e) {
                System.out.println("There is no such customer! Try again!");
            }
        }
    }

    public int chooseCustomer() {
        while (true) {
            System.out.println("Enter customer id:");
            String answer = sc.nextLine();
            try {
                int ans = Integer.parseInt(answer);
                return ans;
            } catch (Exception e) {
                System.out.println("There is no such customer! Try again!");
            }
        }
    }


    private String chooseName() {
        System.out.println("Enter name: ");
        return sc.nextLine().trim();
    }
}
