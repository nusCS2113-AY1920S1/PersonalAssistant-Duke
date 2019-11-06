package frontend;

public class UiDummy implements Ui {
    public static String uiTestString;

    /**
     * Creates a user interface object.
     */
    public UiDummy() {
        uiTestString = "";
    }

    /**
     * Prints the message in the terminal.
     * @param message to be printed.
     */
    public void show(String message) {
        uiTestString += "show";
    }

    /**
     * Prints the exit message.
     */
    public void showExit() {
        uiTestString += "exit";
    }

    /**
     * Prints an error in the terminal.
     * @param message to be printed as an error.
     */
    public void showError(String message) {
        uiTestString += "error";
    }

    /**
     * Prints a warning in the terminal.
     * @param message as the warning message.
     */
    public void showWarning(String message) {
        uiTestString += "warning";
    }

    /**
     * Clears the screen.
     */
    public void clearScreen() {
        uiTestString += "clear";
    }

    /**
     * Prints a message as an info.
     * @param message as the info message.
     */
    public void showInfo(String message) {
        uiTestString += "info";
    }

    /**
     * Gets user input.
     * @return the user input.
     */
    public String getInput() {
        uiTestString += "input";
        return "";
    }

    /**
     * Delays the program.
     * @param delay time in milliseconds.
     */
    public void sleep(int delay) {
        uiTestString += "sleep";
    }

    public void showHint(String text) {
        uiTestString += "hint";
    }

    /**
     * Prints text to the terminal type writer style.
     * @param text to be printed.
     * @param hasPressEnter if 'Press ENTER' should be added to the print.
     */
    public void typeWriter(String text, boolean hasPressEnter) {
        uiTestString += "typewriter";
    }
}
