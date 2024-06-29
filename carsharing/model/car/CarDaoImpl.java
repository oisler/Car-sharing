package carsharing.model.car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import carsharing.model.BaseDao;

public class CarDaoImpl extends BaseDao implements CarDao {

  private static final String CREATE_TABLE = """
      CREATE TABLE IF NOT EXISTS CAR (
      ID INT PRIMARY KEY AUTO_INCREMENT,
      NAME VARCHAR(255) NOT NULL UNIQUE,
      IS_RENTED BOOLEAN,
      COMPANY_ID INT NOT NULL,
      CONSTRAINT FK_COMPANY FOREIGN KEY (COMPANY_ID)
      REFERENCES COMPANY(ID)
      ON DELETE CASCADE
      );
      """;
  private static final String DROP_TABLE = "DROP TABLE CAR IF EXISTS";
  private static final String SELECT_ALL_BY_COMPANY = "SELECT * FROM CAR WHERE COMPANY_ID = (?) ORDER BY ID;";
  private static final String INSERT = "INSERT INTO CAR VALUES (?, ?, ?, ?);";
  private static final String FIND_CAR_BY_ID = "SELECT * FROM CAR WHERE ID = (?);";
  private static final String UPDATE = "UPDATE CAR SET IS_RENTED = (?) WHERE ID = (?)";

  public CarDaoImpl(String dbName) {
    super(dbName);
  }

  @Override
  public List<Car> findAll(int companyId) {
    try {
      return doFindAll(companyId);
    } catch (SQLException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private List<Car> doFindAll(int companyId) throws SQLException, ClassNotFoundException {
    List<Car> cars = new ArrayList<>();

    try (Connection con = establishConnection()) {
      PreparedStatement stmt = con.prepareStatement(SELECT_ALL_BY_COMPANY);
      stmt.setInt(1, companyId);

      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        int carId = rs.getInt("id");
        String carName = rs.getString("name");
        boolean isRented = rs.getBoolean("is_rented");
        int carCompanyId = rs.getInt("company_id");
        Car car = new Car(carId, carName, carCompanyId, isRented);
        cars.add(car);
      }
    }

    return cars;
  }

  @Override
  public void add(String carName, int companyId) {
    try {
      doAdd(carName, companyId);
      System.out.println("The car was added!");
    } catch (SQLException | ClassNotFoundException e) {
      System.out.printf("Car with carName: %s could not be added.", carName);
    }
  }

  private void doAdd(String carName, int companyId) throws SQLException, ClassNotFoundException {
    try (Connection con = establishConnection()) {
      PreparedStatement stmt = con.prepareStatement(INSERT);
      stmt.setNull(1, Types.INTEGER);
      stmt.setString(2, carName);
      stmt.setBoolean(3, false);
      stmt.setInt(4, companyId);
      stmt.execute();
    }
  }

  @Override
  public Car findById(int id) {
    try {
      return doFindById(id);
    } catch (SQLException | ClassNotFoundException e) {
      System.out.printf("Car with ID: %d not found.", id);
      return null;
    }
  }

  private Car doFindById(int id) throws SQLException, ClassNotFoundException {
    try (Connection con = establishConnection()) {
      PreparedStatement stmt = con.prepareStatement(FIND_CAR_BY_ID);
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();

      Car car = null;
      while (rs.next()) {
        int carId = rs.getInt("id");
        String carName = rs.getString("name");
        boolean isRented = rs.getBoolean("is_rented");
        int companyId = rs.getInt("company_id");
        car = new Car(carId, carName, companyId, isRented);
      }

      return car;
    }
  }

  @Override
  public void update(Car car) {
    try {
      doUpdate(car);
    } catch (SQLException | ClassNotFoundException e) {
      System.out.printf("Car %d could not be updated.", car.getId());
    }
  }

  private void doUpdate(Car car) throws SQLException, ClassNotFoundException {
    try (Connection con = establishConnection()) {
      PreparedStatement stmt = con.prepareStatement(UPDATE);
      stmt.setBoolean(1, car.isRented());
      stmt.setInt(2, car.getId());
      stmt.executeUpdate();
    }
  }

  public void createTable() {
    try {
      super.runSQL(CREATE_TABLE);
    } catch (SQLException | ClassNotFoundException e) {
      System.out.println("Table car could not be created.");
    }
  }

  public void dropTable() {
    try {
      super.runSQL(DROP_TABLE);
    } catch (SQLException | ClassNotFoundException e) {
      System.out.println("Table car could not be dropped.");
    }
  }
}
