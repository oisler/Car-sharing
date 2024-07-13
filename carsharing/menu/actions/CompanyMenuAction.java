package carsharing.menu.actions;

import java.util.Arrays;
import java.util.regex.Pattern;

public enum CompanyMenuAction {

    BACK(0, "Back"),
    CHOOSE_COMPANY(1, "Company list"),
    CREATE_COMPANY(2, "Create a company");

    public final int code;
    public final String label;

    CompanyMenuAction(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public static boolean isValid(String userInput) {
        return Pattern.matches("[0-2]", userInput);
    }

    public static CompanyMenuAction from(String userInput) {
        return Arrays.stream(CompanyMenuAction.values())
                .filter(action -> action.code == Integer.parseInt(userInput))
                .findFirst()
                .orElseThrow();
    }

    public static void printMenu() {
        System.out.printf("""
                                    
                        %d. %s
                        %d. %s
                        %d. %s
                        """,
                CHOOSE_COMPANY.code, CHOOSE_COMPANY.label,
                CREATE_COMPANY.code, CREATE_COMPANY.label,
                BACK.code, BACK.label);
    }

}
