package duke;

import duke.commands.Command;
import duke.exceptions.DukeException;

public class Duke {
    private TaskList tasks;
    private Ui userInterface;
    private Storage dukeData;
    private boolean shutdown = false;

    /**
     * Full Constructor for CLI/GUI version of Duke.
     * @param filePath A String representing path to the storage file on hard disk.
     */
    public Duke(String filePath, boolean cliMode) {
        userInterface = new Ui();
        try {
            dukeData = new Storage(filePath);
            tasks = new TaskList(dukeData.loadData());
        } catch (DukeException e) {
            if (cliMode) {
                userInterface.print(userInterface.showFormatted(e.getMessage()));
            }
            tasks = new TaskList();
        } finally {
            if (cliMode) {
                this.startDukeCli();
            }
        }
    }

    /**
     * Default Constructor, Entry point into this java program, for CLI version.
     */
    public Duke() {
        this("data/duke.txt", false);
    }

    /**
     * Entry point into this java program, for CLI version.
     */
    public static void main(String[] args) {
        Duke dukeInstance = new Duke("data/duke.txt", true);
    }

    /**
     * CLI mode - the String returned by getResponse is to be printed to the console.
     */
    private void startDukeCli() {
        userInterface.print(userInterface.showWelcome());

        while (!this.isShutdown()) {
            String input = userInterface.readInput();
            userInterface.print(getResponse(input));
        }
    }

    /**
     * Method to interact with Duke.
     * @param input String input from GUI/CLI layer.
     * @return output response String to be returned to GUI/CLI.
     */
    public String getResponse(String input) {
        try {
            Command command = Parser.parse(input);
            String response = command.execute(tasks, dukeData, userInterface);
            this.setShutdown(command.isExit());
            return response;
        } catch (DukeException e) {
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
