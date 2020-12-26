package ru.vsu.ui;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.vsu.service.CarService;
import ru.vsu.service.CustomerService;

import java.util.MissingFormatArgumentException;
import java.util.Scanner;

@Getter @Setter
@Builder
@AllArgsConstructor
public class ViewMenu {
    Scanner sc;
    CarService carService;
    CustomerService customerService;
    CarMenu carMenu;
    CustomerMenu customerMenu;
    Printer printer;

    public void show() {
        sc = new Scanner(System.in);
        while(true) {
            System.out.println("Main menu");
            System.out.println("1. Show dealer cars");
            System.out.println("2. Add dealer car");
            System.out.println("3. Edit dealer car");
            System.out.println("4. Show customers");
            System.out.println("5. Edit customer");
            System.out.println("6. Show all sold cars");
            System.out.println("7. Sell car");
            System.out.println("0. Exit");
            String answer = sc.nextLine();
            try {
                switch (Integer.parseInt(answer)) {
                    case 0 :
                        System.exit(0);
                        break;
                    case 1 :
                        showDealerCarInfo();
                        break;
                    case 2 :
                        carMenu.showAdd();
                        break;
                    case 3 :
                        carMenu.showEdit();
                        break;
                    case 4 :
                        showCustomersInfo();
                        break;
                    case 5 :
                        customerMenu.showEdit();
                        break;
                    case 6 :
                        showSoldCarInfo();
                        break;
                    case 7 :
                        customerMenu.showSellCar();
                        break;
                    default:
                        System.out.println("There is no such command! Try again!");
                        break;
                }
            } catch (MissingFormatArgumentException e) {
                System.out.println("There is no such command! Try again!");
            }
        }
    }

    private void showSoldCarInfo() {
        System.out.println("Sold cars");
        printer.printSoldCars(carService.getAllSoldCar());
}

    private void showDealerCarInfo() {
        System.out.println("Dealer cars:");
        printer.printCars(carService.getAllCars());
    }

    private void showCustomersInfo() {
        System.out.println("Customers:");
        printer.printCustomers(customerService.getAllCustomers());

    }

}
