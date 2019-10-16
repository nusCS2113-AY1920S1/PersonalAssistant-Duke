package spinbox;

import spinbox.commands.Command;
import spinbox.exceptions.FileCreationException;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.StorageException;
import spinbox.lists.ModuleContainer;

import java.util.ArrayDeque;
import java.util.HashMap;

public class SpinBox {
    private Ui userInterface;
    private ModuleContainer modules;
    private ArrayDeque<String> pageTrace;
    private boolean shutdown = false;

    /**
     * Full Constructor for CLI/GUI version of SpinBox.
     */
    public SpinBox(boolean cliMode) {
        userInterface = new Ui();
        pageTrace = new ArrayDeque<>();
        pageTrace.add("main");

        try {
            modules = new ModuleContainer();
        } catch (FileCreationException e) {
            userInterface.showFormatted(e.getMessage());
        }

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
            userInterface.print(userInterface.showPage(pageTrace));
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
        try {
            Parser.setPageTrace(pageTrace);
            Command command = Parser.parse(input);
            String response = command.execute(modules.getModules(), pageTrace, userInterface);
            this.setShutdown(command.isExit());
            return response;
        } catch (SpinBoxException e) {
            return userInterface.showFormatted(e.getMessage());
        }

    }

    private void setShutdown(boolean shutdown) {
        this.shutdown = shutdown;
    }

    public boolean isShutdown() {
        return shutdown;
    }
}
