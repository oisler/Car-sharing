package carsharing.model.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import carsharing.model.BaseDao;

public class CustomerDaoImpl extends BaseDao implements CustomerDao {
  private static final String CREATE_TABLE = """
      CREATE TABLE IF NOT EXISTS CUSTOMER (
      ID INT PRIMARY KEY AUTO_INCREMENT,
      NAME VARCHAR(255) NOT NULL UNIQUE,
      RENTED_CAR_ID INT,
      CONSTRAINT FK_CAR FOREIGN KEY (RENTED_CAR_ID)
      REFERENCES CAR(ID)
      );
      """;
  private static final String DROP_TABLE = "DROP TABLE CUSTOMER IF EXISTS";
  private static final String INSERT = "INSERT INTO CUSTOMER VALUES (?, ?, ?);";
  private static final String SELECT_ALL = "SELECT * FROM CUSTOMER ORDER BY ID;";
  private static final String FIND_BY_ID = "SELECT * FROM CUSTOMER WHERE ID = (?)";
  private static final String UPDATE = "UPDATE CUSTOMER SET RENTED_CAR_ID = (?) WHERE ID = (?)";

  public CustomerDaoImpl(String dbName) {
    super(dbName);
  }

  @Override
  public void add(String customerName) {
    try {
      doAdd(customerName);
      System.out.println("The customer was added!\n");
    } catch (SQLException | ClassNotFoundException e) {
      System.out.printf("Customer with name: %s could not be created.", customerName);
    }
  }

  private void doAdd(String customerName) throws SQLException, ClassNotFoundException {
    try (Connection con = establishConnection()) {
      PreparedStatement stmt = con.prepareStatement(INSERT);
      stmt.setNull(1, Types.INTEGER);
      stmt.setString(2, customerName);
      stmt.setNull(3, Types.INTEGER);
      stmt.execute();
    }
  }

  @Override
  public List<Customer> findAll() {
    try {
      return doFindAll();
    } catch (SQLException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private List<Customer> doFindAll() throws SQLException, ClassNotFoundException {
    List<Customer> customers = new ArrayList<>();
    try (Connection con = establishConnection()) {
      PreparedStatement stmt = con.prepareStatement(SELECT_ALL);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        int customerId = rs.getInt("id");
        String name = rs.getString("name");
        int rentedCarId = rs.getInt("rented_car_id");
        Customer customer = new Customer(customerId, name, rentedCarId);
        customers.add(customer);
      }
    }

    return customers;
  }

  @Override
  public Customer findById(int id) {
    try {
      return doFindById(id);
    } catch (SQLException | ClassNotFoundException e) {
      System.out.printf("Company with ID: %d not found.", id);
      return null;
    }
  }

  private Customer doFindById(int id) throws SQLException, ClassNotFoundException {
    try (Connection con = establishConnection()) {
      PreparedStatement stmt = con.prepareStatement(FIND_BY_ID);
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();

      Customer customer = null;
      while (rs.next()) {
        int customerId = rs.getInt("id");
        String name = rs.getString("name");
        int rentedCarId = rs.getInt("rented_car_id");
        customer = new Customer(customerId, name, rentedCarId);
      }

      return customer;
    }
  }

  @Override
  public void update(Customer customer) {
    try {
      doUpdate(customer);
    } catch (SQLException | ClassNotFoundException e) {
      System.out.printf("Customer %s coulds not be updated.", customer.getName());
    }
  }

  private void doUpdate(Customer customer) throws SQLException, ClassNotFoundException {
    try (Connection con = establishConnection()) {
      PreparedStatement stmt = con.prepareStatement(UPDATE);
      if (customer.getRentedCarId() == 0) {
        stmt.setNull(1, Types.INTEGER);
      } else {
        stmt.setInt(1, customer.getRentedCarId());
      }
      stmt.setInt(2, customer.getId());
      stmt.executeUpdate();
    }
  }

  public void createTable() {
    try {
      super.runSQL(CREATE_TABLE);
    } catch (SQLException | ClassNotFoundException e) {
      System.out.println("Table customer could not be created.");
    }
  }

  public void dropTable() {
    try {
      super.runSQL(DROP_TABLE);
    } catch (SQLException | ClassNotFoundException e) {
      System.out.println("Table customer could not be dropped.");
    }
  }

}
