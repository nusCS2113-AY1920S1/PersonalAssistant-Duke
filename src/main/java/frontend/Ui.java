package frontend;

import exceptions.FarmioFatalException;
import farmio.Level;

import java.util.Scanner;

public interface Ui {
    /**
     * Prints the message in the terminal.
     * @param message to be printed.
     */
    void show(String message);

    /**
     * Prints the exit message.
     */
    void showExit();

    /**
     * Prints an error in the terminal.
     * @param message to be printed as an error.
     */
    void showError(String message);

    /**
     * Prints a warning in the terminal.
     * @param message as the warning message.
     */
    void showWarning(String message);

    /**
     * Clears the screen.
     */
    void clearScreen();

    /**
     * Prints a message as an info.
     * @param message as the info message.
     */
    void showInfo(String message);

    /**
     * Gets user input.
     * @return the user input.
     */
    String getInput();

    /**
     * Delays the program.
     * @param delay time in milliseconds.
     */
    void sleep(int delay);

    /**
     * shows a hint
     * @param text the text to be shown as a hint
     */
    void showHint(String text);


    /**
     * Prints text to the terminal type writer style.
     * @param text to be printed.
     * @param hasPressEnter if 'Press ENTER' should be added to the print.
     */
    void typeWriter(String text, boolean hasPressEnter);

    /**
     * Prints the Narrative of a given level with a simulation instance.
     * @param level that the narrative is to be shown.
     * @param simulation that the simulation of the level will utilise.
     * @throws FarmioFatalException if simulation file is not found
     */
    void showNarrative(Level level, Simulation simulation) throws FarmioFatalException;
}
