package dolla.action;

import dolla.ui.ActionUi;

public class Repeat {
    private static String entryInput = null;
    private static String debtInput = null;
    private static String limitInput = null;

    //@@author yetong1895
    /**
     * This method will set the respective input in this class with the userInput
     * with respect to the mode that the user is in.
     * @param mode      the mode that the user is in.
     * @param userInput the input entered by the user.
     */
    public static void setRepeatInput(String mode, String userInput) {
        if (mode.equals("entry")) {
            Repeat.entryInput = userInput;
        } else if (mode.equals("debt")) {
            Repeat.debtInput = userInput;
        } else {
            Repeat.limitInput = userInput;
        }
    }

    /**
     * This method will return the respective input to be repeat in this class with
     * respect to the mode that the user is in.
     * @param mode the mode that the user is in.
     * @return the input to be repeated.
     */
    public static String getRepeatInput(String mode) {
        if (mode.equals("entry") && entryInput != null) {
            return entryInput;
        } else if (mode.equals("debt") && entryInput != null) {
            return debtInput;
        } else if (mode.equals("limit") && entryInput != null) {
            return limitInput;
        } else {
            ActionUi.printEmptyRepeatError();
            return "null";
        }
    }
}
