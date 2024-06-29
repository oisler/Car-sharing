package carsharing.menu.actions;

import java.util.Arrays;
import java.util.regex.Pattern;

public enum CompanyCarMenuAction {

  BACK(0, "Back"),
  CHOOSE_CAR(1, "Car list"),
  CREATE_CAR(2, "Create a car");

  public final int code;
  public final String label;

  CompanyCarMenuAction(int code, String label) {
    this.code = code;
    this.label = label;
  }

  public static boolean isValid(String userInput) {
    return Pattern.matches("[0-2]", userInput);
  }

  public static CompanyCarMenuAction from(String userInput) {
    return Arrays.stream(CompanyCarMenuAction.values())
        .filter(action -> action.code == Integer.parseInt(userInput))
        .findFirst()
        .orElseThrow();
  }
}
