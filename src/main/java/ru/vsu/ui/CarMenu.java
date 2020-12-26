package ru.vsu.ui;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.vsu.data.entity.Car;
import ru.vsu.data.entity.CarType;
import ru.vsu.data.entity.Customer;
import ru.vsu.service.CarService;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class CarMenu {
    Scanner sc = new Scanner(System.in);
    Printer printer;
    CarService carService;

    public CarMenu(Printer printer, CarService carService) {
        this.printer = printer;
        this.carService = carService;
    }

    public void showAdd() {
        String brand = showBrandMenu();
        CarType carType = showCarTypeMenu();
        String model = showModelMenu();
        int size = carService.getAllCars().size();
        carService.createCar(new Car(size + 1, carType, brand, model, null));
        return;
    }

    public void showEdit() {
        int carId = showCarMenu();
        String brand = showBrandMenu();
        CarType carType = showCarTypeMenu();
        String model = showModelMenu();
        carService.editCar(carId, new Car(carId, carType, brand, model, null));
        return;
    }

    public int showCarMenu() {
        while (true) {
            System.out.println("Enter car id:");
            String answer = sc.nextLine();
            try {
                int ans = Integer.parseInt(answer);
                return ans;
            } catch (Exception e) {
                System.out.println("There is no such car! Try again!");
            }
        }
    }

    private String showBrandMenu() {
        while (true) {
            System.out.println("Choose brand:");
            String answer = sc.nextLine();
            if (answer.length() == 0) {
                System.out.println("There is no such brand! Try again!");
            } else {
                return answer;
            }
        }
    }

    private CarType showCarTypeMenu() {
        while (true) {
            System.out.println("Choose car type:");
            String answer = sc.nextLine();
            try {
                return CarType.valueOf(answer.toUpperCase());
            } catch(IllegalArgumentException e) {
                System.out.println("There is no such car type! Try again!");
            }
        }
    }


    private String showModelMenu() {
        while (true) {
            System.out.println("Choose model:");
            String answer = sc.nextLine();
            if (answer.length() == 0) {
                System.out.println("There is no such model! Try again!");
            } else {
                return answer;
            }
        }
    }
}
