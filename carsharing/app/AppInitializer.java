package carsharing.app;

import java.util.List;

public final class AppInitializer {

  private static final String DB_NAME = "-databaseFileName";
  private static final String DEFAULT_DB_NAME = "carsharing";

  public static CarSharingApp initialize(List<String> args) {
    String dbName = args.contains(DB_NAME) ? args.get(args.indexOf(DB_NAME) + 1) : DEFAULT_DB_NAME;
    return new CarSharingApp(dbName);
  }

}
