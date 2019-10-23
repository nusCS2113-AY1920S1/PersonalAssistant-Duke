package dolla.action;

public class Repeat {
    private static String entryInput;
    private static String debtInput;
    private static String limitInput;

    public static void setUserInput(String mode, String userInput) {
        if (mode.equals("entry")) {
            Repeat.entryInput = userInput;
        } else if (mode.equals("debt")) {
            Repeat.debtInput = userInput;
        } else {
            Repeat.limitInput = userInput;
        }
    }

    public static String getUserInput(String mode) {
        if (mode.equals("entry")) {
            return entryInput;
        } else if (mode.equals("debt")) {
            return debtInput;
        } else {
            return limitInput;
        }
    }
}
