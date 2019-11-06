package frontend;

import exceptions.FarmioFatalException;
import farmio.Level;

import java.util.Scanner;

public class UiManager implements Ui {
    private Scanner scanner;
    private String clearScreen = "\033c" + "\033[2J";

    /**
     * Creates a user interface object.
     */
    public UiManager() {
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
     * Prints the message in the terminal without a new line.
     * @param message to be printed.
     */
    private void print(String message) {
        System.out.print(message);
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
    public void clearScreen() {
        System.out.println(clearScreen);
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
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            clearScreen();
            showWarning("Simulator refersh interrupted! Interface may not display correctly.");
        }
    }

    /**
     * Prints a hint on to the terminal.
     * @param text the text to be shown as a hint
     */
    public void showHint(String text) {
        show(AsciiColours.YELLOW
                + "Hint:"
                + AsciiColours.SANE);
        show(text);
        show("~.Enter [Start] when you are ready to complete the objective");
    }

    /**
     * Shows the level begin String.
     */
    private void showLevelBegin() {
        show("\n"
                + " ".repeat(GameConsole.FULL_CONSOLE_WIDTH / 2 - 8)
                + AsciiColours.GREEN
                + AsciiColours.UNDERLINE
                + "[LEVEL BEGIN]"
                + AsciiColours.SANE
                + "\n\n       "
                + "Enter [start] if you are ready to complete the objective or Enter [hint] if you get stuck!");
    }

    /**
     * Prints text to the terminal type writer style.
     * @param text to be printed.
     * @param hasPressEnter if 'Press ENTER' should be added to the print.
     */
    public void typeWriter(String text, boolean hasPressEnter) {
        int lineLength = 0;
        if (!text.isBlank()) {
            print(">>> ");
        }
        sleep(150);
        for (int i = 0; i < text.length(); i++) {
            System.out.printf("%c", text.charAt(i));
            lineLength++;
            if (lineLength > GameConsole.FULL_CONSOLE_WIDTH - 10 && text.charAt(i) == ' ') {
                print("\n    ");
                lineLength = 0;
            } else if (text.charAt(i) == '\n') {
                print("    ");
                lineLength = 0;
            }
            sleep(10);
        }
        if (hasPressEnter) {
            show("\n\n" + " ".repeat(GameConsole.FULL_CONSOLE_WIDTH - GameConsole.USER_CODE_SECTION_WIDTH)
                    + "Press [ENTER] to continue..");
        }
        show("");
    }

    /**
     * Prints the Narrative of a given level with a simulation instance.
     * @param level that the narrative is to be shown.
     * @param simulation that the simulation of the level will utilise.
     * @throws FarmioFatalException if simulation file is not found
     */
    public void showNarrative(Level level, Simulation simulation) throws FarmioFatalException {
        int frameId = 0;
        int lastFrameId = level.getNarratives().size() - 1;
        for (String narrative: level.getNarratives()) {
            String userInput = getInput();
            if (userInput.toLowerCase().equals("skip") || frameId == lastFrameId) {
                break;
            }
            simulation.simulate(level.getPath(), frameId++);
            typeWriter(narrative, true);
        }
        simulation.simulate(level.getPath(), lastFrameId);
        typeWriter(level.getNarratives().get(lastFrameId), false);
        showLevelBegin();
    }
}
