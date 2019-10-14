package spinbox;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

public class SpinBox {
    private Ui userInterface;
    private HashMap<String, Module> modules;
    private Deque<String> page;
    private boolean shutdown = false;

    /**
     * Full Constructor for CLI/GUI version of SpinBox.
     */
    public SpinBox(boolean cliMode) {
        userInterface = new Ui();
        modules = new HashMap<>();
        page = new ArrayDeque<>();

        if (cliMode) {
            this.startSpinBoxCli();
        }
    }

    /**
     * Default Constructor, Entry point into this java program, for CLI version.
     */
    public SpinBox() {
        this(false);
    }

    /**
     * Entry point into this java program, for CLI version.
     */
    public static void main(String[] args) {
        new SpinBox(true);
    }

    /**
     * CLI mode - the String returned by getResponse is to be printed to the console.
     */
    private void startSpinBoxCli() {
        userInterface.print(userInterface.showWelcome());

        while (!this.isShutdown()) {
            String input = userInterface.readInput();
            userInterface.print(getResponse(input));
        }
    }

    /**
     * Method to interact with SpinBox.
     * @param input String input from GUI/CLI layer.
     * @return output response String to be returned to GUI/CLI.
     */
    public String getResponse(String input) {
        /*
        Need to implement parser and see what needs to be passed into userInterface.
        try {
            Command command = Parser.parse(input);
            String response;
            if (!command.isFileCommand()) {
                response = command.execute(modules, dukeData, userInterface);
            } else {
                response = command.execute(modules, fileData, userInterface);
            }
            this.setShutdown(command.isExit());
            return response;
        } catch (DukeException e) {
            return userInterface.showFormatted(e.getMessage());
        }
         */
        this.shutdown = true;
        return null;
    }

    private void setShutdown(boolean shutdown) {
        this.shutdown = shutdown;
    }

    public boolean isShutdown() {
        return shutdown;
    }
}
