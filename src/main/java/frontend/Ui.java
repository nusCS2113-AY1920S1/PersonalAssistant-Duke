package frontend;

import java.util.Scanner;

public class Ui {
    private Scanner scanner;
    private final String CLEAR_SCREEN = "\033c" + "\033[2J";

    /**
     * Creates a user interface object.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prints the message in the terminal.
     * @param message to be printed.
     */
    public void show(String message) {
        System.out.println(message);
    }

    /**
     * Prints the exit message.
     */
    public void showExit() {
        typeWriter("Goodbye.", false);
    }

    /**
     * Prints an error in the terminal.
     * @param message to be printed as an error.
     */
    public void showError(String message) {
        show("Error: " + message);
    }

    /**
     * Prints a warning in the terminal.
     * @param message as the warning message.
     */
    public void showWarning(String message) {
        show(AsciiColours.RED + "Warning: " + message + AsciiColours.SANE);
    }

    /**
     * Clears the screen.
     */
    void clearScreen() {
        System.out.println(CLEAR_SCREEN);
    }

    /**
     * Prints a message as an info.
     * @param message as the info message.
     */
    public void showInfo(String message) {
        show(AsciiColours.CYAN + "Info: " + AsciiColours.SANE + message);
    }

    /**
     * Gets user input.
     * @return the user input.
     */
    public String getInput() {
        show("\nInput: ");
        return scanner.nextLine().replace("[","").replace("]","");
    }

    /**
     * Delays the program.
     * @param delay time in milliseconds.
     */
    public void sleep(int delay) {
        try {
            Thread.sleep(delay);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
            clearScreen();
            showWarning("Simulator refersh interrupted! Interface may not display correctly.");
        }
    }

    public void showHint(String text) {
        show(AsciiColours.YELLOW + "Hint:" + AsciiColours.SANE);
        show(text);
        show("~.Enter [Start] when you are ready to complete the objective");
    }

    /**
     * Prints text to the terminal type writer style.
     * @param text to be printed.
     * @param hasPressEnter if 'Press ENTER' should be added to the print.
     */
    public void typeWriter(String text, boolean hasPressEnter) { //TODO clean this method up
        final char LEVEL_BEGIN_PLACEHOLDER = '~';
        boolean isNewline = false;
        int lineLength = 0;
        if (!text.isBlank()) System.out.print(">>> ");
        sleep(150);
        for (int i = 0; i < text.length(); i++) {
            lineLength++;
            if (lineLength > GameConsole.FULL_CONSOLE_WIDTH - 10 && text.charAt(i) == ' ') {
                System.out.print("\n   ");
                lineLength = 0;
            } else if (text.charAt(i) == '\n') {
                isNewline = true;
                lineLength = 0;
            } else if (text.charAt(i) == LEVEL_BEGIN_PLACEHOLDER) {
                System.out.println("\n" + " ".repeat(GameConsole.FULL_CONSOLE_WIDTH / 2 - 8) + AsciiColours.GREEN
                        + AsciiColours.UNDERLINE + "[LEVEL BEGIN]" + AsciiColours.SANE + "\n");
                show("       Enter [Start] if you are ready to complete the objective or Enter [hint] if you get stuck!");
                return;
            }
            else {
                System.out.printf("%c", text.charAt(i));
            }
            if (isNewline) {
                System.out.print("\n    ");
                isNewline = false;
            }
            sleep(10);
        }
        if (hasPressEnter) {
            show("\n\n" + " ".repeat(GameConsole.FULL_CONSOLE_WIDTH - GameConsole.USER_CODE_SECTION_WIDTH)
                    + "Press [ENTER] to continue..");
        }
        show("");
    }
}