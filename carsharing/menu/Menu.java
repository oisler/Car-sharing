package carsharing.menu;

import carsharing.menu.actions.CompanyCarMenuAction;
import carsharing.menu.actions.CompanyMenuAction;
import carsharing.menu.actions.CustomerCarMenuAction;
import carsharing.menu.actions.LoginMenuAction;
import carsharing.model.car.Car;
import carsharing.model.company.Company;
import carsharing.model.customer.Customer;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public final class Menu {

    private static final Scanner SCANNER = new Scanner(System.in);

    public LoginMenuAction chooseLoginMenuAction() {
        LoginMenuAction.printMenu();

        String userInput = SCANNER.nextLine();
        while (!LoginMenuAction.isValid(userInput)) {
            userInput = SCANNER.nextLine();
        }

        return LoginMenuAction.from(userInput);
    }

    public CompanyMenuAction chooseCompanyMenuAction() {
        CompanyMenuAction.printMenu();

        String userInput = SCANNER.nextLine();
        while (!CompanyMenuAction.isValid(userInput)) {
            userInput = SCANNER.nextLine();
        }

        return CompanyMenuAction.from(userInput);
    }

    public int chooseCompany(List<Company> companies) {
        companies.forEach(System.out::println);
        System.out.println("0. Back");

        String userInput = SCANNER.nextLine();
        while (!Pattern.matches("\\d+", userInput) || (Integer.parseInt(userInput) < 0 || Integer.parseInt(userInput) > companies.size())) {
            System.out.println("Invalid input!");
            userInput = SCANNER.nextLine();
        }

        return Integer.parseInt(userInput);
    }

    public int chooseCar(List<Car> car) {
        car.forEach(System.out::println);
        System.out.println("0. Back");

        String userInput = SCANNER.nextLine();
        while (!Pattern.matches("\\d+", userInput) || (Integer.parseInt(userInput) < 0 || Integer.parseInt(userInput) > car.size())) {
            System.out.println("Invalid input!");
            userInput = SCANNER.nextLine();
        }

        return Integer.parseInt(userInput);
    }

    public int chooseCustomer(List<Customer> customers) {
        customers.forEach(System.out::println);
        System.out.println("0. Back");

        String userInput = SCANNER.nextLine();
        while (!Pattern.matches("\\d+", userInput) || (Integer.parseInt(userInput) < 0 || Integer.parseInt(userInput) > customers.size())) {
            System.out.println("Invalid input!");
            userInput = SCANNER.nextLine();
        }

        return Integer.parseInt(userInput);
    }

    public CompanyCarMenuAction chooseCarMenuAction() {
        CompanyCarMenuAction.printMenu();

        String userInput = SCANNER.nextLine();
        while (!CompanyCarMenuAction.isValid(userInput)) {
            userInput = SCANNER.nextLine();
        }

        return CompanyCarMenuAction.from(userInput);
    }

    public String getName() {
        return SCANNER.nextLine();
    }

    public CustomerCarMenuAction chooseCustomerCarMenuAction() {
        CustomerCarMenuAction.printMenu();

        String userInput = SCANNER.nextLine();
        while (!CustomerCarMenuAction.isValid(userInput)) {
            userInput = SCANNER.nextLine();
        }

        return CustomerCarMenuAction.from(userInput);
    }
}
