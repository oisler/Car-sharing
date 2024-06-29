package carsharing;

import java.util.List;

import carsharing.app.AppInitializer;
import carsharing.app.CarSharingApp;

public final class Main {

  public static void main(String[] args) {
    CarSharingApp app = AppInitializer.initialize(List.of(args));
    app.runSQLs();
    app.run();
  }

}
