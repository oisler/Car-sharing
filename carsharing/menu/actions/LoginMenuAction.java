package carsharing.menu.actions;

import java.util.Arrays;
import java.util.regex.Pattern;

public enum LoginMenuAction {

  EXIT(0, "Exit"),
  LOGIN_MANAGER(1, "Log in as a manager"),
  LOGIN_CUSTOMER(2, "Log in as a customer"),
  CREATE_CUSTOMER(3, "Create a customer");

  public final int code;
  public final String label;

  LoginMenuAction(int code, String label) {
    this.code = code;
    this.label = label;
  }

  public static boolean isValid(String userInput) {
    return Pattern.matches("[0-3]", userInput);
  }

  public static LoginMenuAction from(String userInput) {
    return Arrays.stream(LoginMenuAction.values())
        .filter(action -> action.code == Integer.parseInt(userInput))
        .findFirst()
        .orElseThrow();
  }
}
