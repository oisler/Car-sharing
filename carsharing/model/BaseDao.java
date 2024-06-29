package carsharing.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class BaseDao {

  private final String dbName;

  protected BaseDao(String dbName) {
    this.dbName = dbName;
  }

  protected Connection establishConnection() throws ClassNotFoundException, SQLException {
    Class.forName("org.h2.Driver");
    Connection connection = DriverManager.getConnection(String.format("jdbc:h2:./src/carsharing/db/%s", dbName));
    connection.setAutoCommit(true);
    return connection;
  }

  protected void runSQL(String sql) throws SQLException, ClassNotFoundException {
    try (Connection connection = establishConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.execute();
    }
  }

}
