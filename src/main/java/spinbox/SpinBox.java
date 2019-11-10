package spinbox;

import spinbox.commands.Command;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.StorageException;
import spinbox.containers.ModuleContainer;

import java.util.ArrayDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpinBox {
    private Ui userInterface;
    private ModuleContainer modules;
    private ArrayDeque<String> pageTrace;
    private boolean shutdown = false;
    private static final Logger LOGGER = Logger.getLogger(SpinBox.class.getName());

    /**
     * Full Constructor for CLI/GUI version of SpinBox.
     */
    public SpinBox(boolean cliMode) throws StorageException {
        LOGGER.setUseParentHandlers(true);
        LOGGER.setLevel(Level.INFO);
        LOGGER.entering(getClass().getName(), "full constructor");
        userInterface = new Ui(cliMode);
        pageTrace = new ArrayDeque<>();
        pageTrace.add("main");
        modules = new ModuleContainer();

        if (cliMode) {
            LOGGER.info("Using CLI mode");
            this.startSpinBoxCli();
        }
        LOGGER.exiting(getClass().getName(), "full constructor");
    }

    /**
     * Default Constructor, Entry point into this java program, for CLI version.
     */
    public SpinBox() throws StorageException {
        this(false);
    }

    /**
     * Entry point into this java program, for CLI version.
     */
    public static void main(String[] args) throws StorageException {
        new SpinBox(true);
    }

    /**
     * CLI mode - the String returned by getResponse is to be printed to the console.
     */
    private void startSpinBoxCli() {
        LOGGER.entering(getClass().getName(), "startSpinBoxCli");
        userInterface.print(userInterface.showWelcome());

        while (!this.isShutdown()) {
            userInterface.print(userInterface.showPage(pageTrace));
            String input = userInterface.readInput();
            userInterface.print(getResponse(input, false));
        }
        LOGGER.exiting(getClass().getName(), "startSpinBoxCli");
    }

    /**
     * Method to interact with SpinBox.
     * @param input String input from GUI/CLI layer.
     * @param guiMode boolean to check if it is running gui.
     * @return output response String to be returned to GUI/CLI.
     */
    public String getResponse(String input, boolean guiMode) {
        LOGGER.entering(getClass().getName(), "getResponse");
        LOGGER.info("User input: " + input + ", from GUI: " + guiMode);
        try {
            Parser.setPageTrace(pageTrace);
            Command command = Parser.parse(input);
            String response = command.execute(modules, pageTrace, userInterface, guiMode);
            this.setShutdown(command.isExit());
            LOGGER.info("Response from SpinBox:\n" + response);
            LOGGER.exiting(getClass().getName(), "getResponse");
            return response;
        } catch (SpinBoxException e) {
            LOGGER.warning(e.getMessage());
            LOGGER.exiting(getClass().getName(), "getResponse");
            return userInterface.showFormatted(e.getMessage());
        }
    }

    private void setShutdown(boolean shutdown) {
        LOGGER.entering(getClass().getName(), "setShutdown");
        this.shutdown = shutdown;
        LOGGER.exiting(getClass().getName(), "setShutdown");
    }

    public boolean isShutdown() {
        return shutdown;
    }

    public ModuleContainer getModuleContainer() {
        return modules;
    }
}