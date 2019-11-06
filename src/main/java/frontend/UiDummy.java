package frontend;

import java.util.Scanner;

public class UiDummy implements Ui {
    public int testValue;

    /**
     * Creates a user interface object.
     */
    public UiDummy() {
        testValue = 0;
    }

    /**
     * Prints the message in the terminal.
     * @param message to be printed.
     */
    public void show(String message) {

    }

    /**
     * Prints the exit message.
     */
    public void showExit() {

    }

    /**
     * Prints an error in the terminal.
     * @param message to be printed as an error.
     */
    public void showError(String message) {
    }

    /**
     * Prints a warning in the terminal.
     * @param message as the warning message.
     */
    public void showWarning(String message) {

    }

    /**
     * Clears the screen.
     */
    public void clearScreen() {

    }

    /**
     * Prints a message as an info.
     * @param message as the info message.
     */
    public void showInfo(String message) {

    }

    /**
     * Gets user input.
     * @return the user input.
     */
    public String getInput() {
        return "";
    }

    /**
     * Delays the program.
     * @param delay time in milliseconds.
     */
    public void sleep(int delay) {

    }

    public void showHint(String text) {

    }

    /**
     * Prints text to the terminal type writer style.
     * @param text to be printed.
     * @param hasPressEnter if 'Press ENTER' should be added to the print.
     */
    public void typeWriter(String text, boolean hasPressEnter) {

    }
}
