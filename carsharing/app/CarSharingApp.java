package carsharing.app;

import carsharing.menu.Menu;
import carsharing.menu.actions.CompanyCarMenuAction;
import carsharing.menu.actions.CompanyMenuAction;
import carsharing.menu.actions.CustomerCarMenuAction;
import carsharing.menu.actions.LoginMenuAction;
import carsharing.model.car.Car;
import carsharing.model.car.CarDaoImpl;
import carsharing.model.company.Company;
import carsharing.model.company.CompanyDaoImpl;
import carsharing.model.customer.Customer;
import carsharing.model.customer.CustomerDaoImpl;

import java.util.List;
import java.util.stream.Collectors;

public final class CarSharingApp {

    private final Menu menu;
    private final CompanyDaoImpl companyDao;
    private final CarDaoImpl carDao;
    private final CustomerDaoImpl customerDao;

    public CarSharingApp(String dbName) {
        this.menu = new Menu();
        this.companyDao = new CompanyDaoImpl(dbName);
        this.carDao = new CarDaoImpl(dbName);
        this.customerDao = new CustomerDaoImpl(dbName);
    }

    public void run() {
        LoginMenuAction action = menu.chooseLoginMenuAction();
        if (LoginMenuAction.EXIT == action) {
            return;
        } else if (LoginMenuAction.LOGIN_MANAGER == action) {
            processManagerMenu();
        } else if (LoginMenuAction.LOGIN_CUSTOMER == action) {
            processCustomerMenu();
        } else if (LoginMenuAction.CREATE_CUSTOMER == action) {
            System.out.println("\nEnter the customer name:");
            customerDao.add(menu.getName());
        }

        run();
    }

    private void processCustomerMenu() {
        int customerId = chooseCustomer();
        if (customerId == 0) {
            return;
        }

        processCustomerCarMenu(customerId);
    }

    private void processCustomerCarMenu(int customerId) {
        CustomerCarMenuAction action = menu.chooseCustomerCarMenuAction();
        if (CustomerCarMenuAction.BACK == action) {
            System.out.println();
            return;
        }

        Customer customer = customerDao.findById(customerId);
        if (CustomerCarMenuAction.RENT_CAR == action) {
            rentCar(customer);
        } else if (CustomerCarMenuAction.RETURN_CAR == action) {
            returnCar(customer);
        } else if (CustomerCarMenuAction.PRINT_CAR == action) {
            printCar(customer);
        }

        processCustomerCarMenu(customer.getId());
    }

    private void rentCar(Customer customer) {
        if (customer.getRentedCarId() != 0) {
            System.out.println("\nYou've already rented a car!\n");
            return;
        }
        List<Company> companies = companyDao.findAll();
        if (companies.isEmpty()) {
            System.out.println("\nThe company list is empty!");
            return;
        }
        System.out.println("\nChoose a company:");
        int chosenCompany = menu.chooseCompany(companies);
        if (chosenCompany == 0) {
            return;
        }
        Company company = companyDao.findById(chosenCompany);
        List<Car> cars = carDao.findAll(company.getId()).stream().filter(car -> !car.isRented()).collect(Collectors.toList());
        if (cars.isEmpty()) {
            System.out.printf("\nNo available cars in the '%s' company\n", company.getName());
            return;
        }
        System.out.println("\nChoose a car:");
        int chosenCar = menu.chooseCar(cars);
        if (chosenCar == 0) {
            return;
        }

        Car car = cars.get(chosenCar - 1);
        car.setRented(true);
        carDao.update(car);

        customer.setRentedCarId(car.getId());
        customerDao.update(customer);
        System.out.printf("\nYou rented '%s'\n", car.getName());
    }

    private void returnCar(Customer customer) {
        if (customer.getRentedCarId() == 0) {
            System.out.println("\nYou didn't rent a car!\n");
        } else {
            Car car = carDao.findById(customer.getRentedCarId());
            car.setRented(false);
            carDao.update(car);

            customer.setRentedCarId(0);
            customerDao.update(customer);
            System.out.println("\nYou've returned a rented car!");
        }
    }

    private void printCar(Customer customer) {
        if (customer.getRentedCarId() == 0) {
            System.out.println("\nYou didn't rent a car!");
        } else {
            Car car = carDao.findById(customer.getRentedCarId());
            Company company = companyDao.findById(car.getCompanyId());
            System.out.printf("""
                      
                      Your rented car:
                      %s
                      Company:
                      %s  \s
                    """, car.getName(), company.getName());
        }
    }

    private void processCarMenu(Company company) {
        CompanyCarMenuAction action = menu.chooseCarMenuAction();
        if (CompanyCarMenuAction.BACK == action) {
            return;
        }

        if (CompanyCarMenuAction.CHOOSE_CAR == action) {
            chooseCar(company.getId());
        } else if (CompanyCarMenuAction.CREATE_CAR == action) {
            System.out.println("\nEnter the car name:");
            carDao.add(menu.getName(), company.getId());
        }

        processCarMenu(company);
    }

    private void chooseCompany() {
        List<Company> companies = companyDao.findAll();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
            return;
        }
        int chosenCompany = menu.chooseCompany(companies);
        if (chosenCompany == 0) {
            return;
        }

        Company company = companyDao.findById(chosenCompany);
        System.out.printf("%n'%s' company", company.getName());
        processCarMenu(company);
    }

    private void chooseCar(int companyId) {
        List<Car> cars = carDao.findAll(companyId);
        if (cars.isEmpty()) {
            System.out.println("\nThe car list is empty!");
        } else {
            System.out.println("\nCar list:");
            int index = 1;
            for (Car car : cars) {
                System.out.printf("%d. %s%n", index, car.getName());
                index++;
            }
        }
    }

    private int chooseCustomer() {
        List<Customer> customers = customerDao.findAll();
        if (customers.isEmpty()) {
            System.out.println("\nThe customer list is empty!\n");
            return 0;
        }
        System.out.println("\nCustomer list:");
        return menu.chooseCustomer(customers);
    }

    private void processManagerMenu() {
        CompanyMenuAction action = menu.chooseCompanyMenuAction();
        if (action == CompanyMenuAction.BACK) {
            System.out.println();
            return;
        }

        if (action == CompanyMenuAction.CHOOSE_COMPANY) {
            System.out.println();
            chooseCompany();
        } else if (action == CompanyMenuAction.CREATE_COMPANY) {
            System.out.println("\nEnter the company name:");
            companyDao.add(menu.getName());
        }

        processManagerMenu();
    }

    public void runSQLs() {
        //    customerDao.dropTable();
        //    carDao.dropTable();
        //    companyDao.dropTable();
//    companyDao.createTable();
//    carDao.createTable();
//    customerDao.createTable();
    }

}
