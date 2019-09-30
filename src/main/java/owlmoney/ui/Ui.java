package owlmoney.ui;

/**
 * Ui handles messages shown to the user of this application.
 * It does not need to be instantiated with any tasks; these are passed
 * as arguments to its methods.
 */

public class Ui {
    /**
     * Prints a message line to the user.
     *
     * @param msg The message to print.
     */
    public void printMessage(String msg) {
        System.out.println(msg);
    }

    /**
     * Prints a greeting message to the user, which happens at startup.
     */
    public void greet(String username) {
        printMessage("Welcome " + username + "!");
        printMessage("Hello! I'm OwlMoney");
        printMessage("What can I do for you?");
    }

    public void firstTimeRun() {
        printMessage("Please enter a username:");
    }

    /**
     * Prints an error message with the given content.
     *
     * @param exceptionMessage The specifics of the error.
     */
    public void printError(String exceptionMessage) {
        printMessage("☹ OOPS!!! " + exceptionMessage);
    }


}
