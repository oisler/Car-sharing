package carsharing.model.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import carsharing.model.BaseDao;

public final class CompanyDaoImpl extends BaseDao implements CompanyDao {

  private static final String CREATE_TABLE = """
      CREATE TABLE IF NOT EXISTS COMPANY (
      ID INT PRIMARY KEY AUTO_INCREMENT,
      NAME VARCHAR(255) NOT NULL UNIQUE
      );
      """;
  private static final String DROP_TABLE = "DROP TABLE COMPANY IF EXISTS";
  private static final String SELECT_ALL = "SELECT * FROM COMPANY ORDER BY ID;";
  private static final String INSERT = "INSERT INTO COMPANY VALUES (?, ?);";
  private static final String FIND_BY_ID = "SELECT * FROM COMPANY WHERE ID = (?)";

  public CompanyDaoImpl(String dbName) {
    super(dbName);
  }

  @Override
  public List<Company> findAll() {
    try {
      return doFindAll();
    } catch (SQLException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private List<Company> doFindAll() throws SQLException, ClassNotFoundException {
    List<Company> companies = new ArrayList<>();

    try (Connection con = establishConnection()) {
      PreparedStatement stmt = con.prepareStatement(SELECT_ALL);
      ResultSet resultSet = stmt.executeQuery();

      while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        Company company = new Company(id, name);
        companies.add(company);
      }
    }

    return companies;
  }

  @Override
  public void add(String companyName) {
    try {
      doAdd(companyName);
      System.out.println("The company was created!");
    } catch (SQLException | ClassNotFoundException e) {
      System.out.printf("Company with name: %s could not be created.", companyName);
    }
  }

  private void doAdd(String companyName) throws SQLException, ClassNotFoundException {
    try (Connection con = establishConnection()) {
      PreparedStatement stmt = con.prepareStatement(INSERT);
      stmt.setNull(1, Types.INTEGER);
      stmt.setString(2, companyName);
      stmt.execute();
    }
  }

  @Override
  public Company findById(int id) {
    try {
      return doFindById(id);
    } catch (SQLException | ClassNotFoundException e) {
      System.out.printf("Company with ID: %d not found.", id);
      return null;
    }
  }

  private Company doFindById(int id) throws SQLException, ClassNotFoundException {
    Company company = null;

    try (Connection con = establishConnection()) {
      PreparedStatement stmt = con.prepareStatement(FIND_BY_ID);
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        company = new Company(rs.getInt("id"), rs.getString("name"));
      }

      return company;
    }
  }

  public void createTable() {
    try {
      super.runSQL(CREATE_TABLE);
    } catch (SQLException | ClassNotFoundException e) {
      System.out.println("Table company could not be created.");
    }
  }

  public void dropTable() {
    try {
      super.runSQL(DROP_TABLE);
    } catch (SQLException | ClassNotFoundException e) {
      System.out.println("Table company could not be dropped.");
    }
  }

}
