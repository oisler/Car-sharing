package carsharing.menu;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import carsharing.menu.actions.CompanyCarMenuAction;
import carsharing.menu.actions.CompanyMenuAction;
import carsharing.menu.actions.CustomerCarMenuAction;
import carsharing.menu.actions.LoginMenuAction;
import carsharing.model.car.Car;
import carsharing.model.company.Company;
import carsharing.model.customer.Customer;

public final class Menu {

  private static final Scanner SCANNER = new Scanner(System.in);

  public LoginMenuAction chooseLoginMenuAction() {
    System.out.printf("""
            %d. %s
            %d. %s
            %d. %s
            %d. %s
            """,
        LoginMenuAction.LOGIN_MANAGER.code, LoginMenuAction.LOGIN_MANAGER.label,
        LoginMenuAction.LOGIN_CUSTOMER.code, LoginMenuAction.LOGIN_CUSTOMER.label,
        LoginMenuAction.CREATE_CUSTOMER.code, LoginMenuAction.CREATE_CUSTOMER.label,
        LoginMenuAction.EXIT.code, LoginMenuAction.EXIT.label);

    String userInput = SCANNER.nextLine();
    while (!LoginMenuAction.isValid(userInput)) {
      userInput = SCANNER.nextLine();
    }

    return LoginMenuAction.from(userInput);
  }

  public CompanyMenuAction chooseCompanyMenuAction() {
    System.out.printf("""
                        
            %d. %s
            %d. %s
            %d. %s
            """,
        CompanyMenuAction.CHOOSE_COMPANY.code, CompanyMenuAction.CHOOSE_COMPANY.label,
        CompanyMenuAction.CREATE_COMPANY.code, CompanyMenuAction.CREATE_COMPANY.label,
        CompanyMenuAction.BACK.code, CompanyMenuAction.BACK.label);

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
    System.out.printf("""
                        
            %d. %s
            %d. %s
            %d. %s
            """,
        CompanyCarMenuAction.CHOOSE_CAR.code, CompanyCarMenuAction.CHOOSE_CAR.label,
        CompanyCarMenuAction.CREATE_CAR.code, CompanyCarMenuAction.CREATE_CAR.label,
        CompanyCarMenuAction.BACK.code, CompanyCarMenuAction.BACK.label);

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
    System.out.printf("""
                        
            %d. %s
            %d. %s
            %d. %s
            %d. %s
            """,
        CustomerCarMenuAction.RENT_CAR.code, CustomerCarMenuAction.RENT_CAR.label,
        CustomerCarMenuAction.RETURN_CAR.code, CustomerCarMenuAction.RETURN_CAR.label,
        CustomerCarMenuAction.PRINT_CAR.code, CustomerCarMenuAction.PRINT_CAR.label,
        CustomerCarMenuAction.BACK.code, CustomerCarMenuAction.BACK.label);

    String userInput = SCANNER.nextLine();
    while (!CustomerCarMenuAction.isValid(userInput)) {
      userInput = SCANNER.nextLine();
    }

    return CustomerCarMenuAction.from(userInput);
  }
}
