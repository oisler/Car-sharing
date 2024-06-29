package carsharing.menu.actions;

import java.util.Arrays;
import java.util.regex.Pattern;

public enum CustomerCarMenuAction {

  BACK(0, "Back"),
  RENT_CAR(1, "Rent a car"),
  RETURN_CAR(2, "Return a rented car"),
  PRINT_CAR(3, "My rented car");

  public final int code;
  public final String label;

  CustomerCarMenuAction(int code, String label) {
    this.code = code;
    this.label = label;
  }

  public static boolean isValid(String userInput) {
    return Pattern.matches("[0-3]", userInput);
  }

  public static CustomerCarMenuAction from(String userInput) {
    return Arrays.stream(CustomerCarMenuAction.values())
        .filter(action -> action.code == Integer.parseInt(userInput))
        .findFirst()
        .orElseThrow();
  }
}
