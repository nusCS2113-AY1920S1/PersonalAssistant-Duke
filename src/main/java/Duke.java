/**
 * Main class that runs Duke and contains the main method for Duke.
 */
public class Duke {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructor that initializes the Task List, Storage and Ui
     * Shows error if the file is empty or does not exit
     */
    public Duke() {
        ui = new Ui();
        storage = new Storage("data/tasks.txt");
        try {
            tasks = new TaskList(storage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the command line interface, reads input from user and returns result accordingly
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit;
            } catch (DukeException e) {
                ui.printException(e);
            }
        }
    }

    /**
     * Returns the response to the GUI when given an input by a user
     *
     * @param input: Input given by user in the GUI
     * @return String: Response to display on GUI by the bot.
     */
    protected String getResponse(String input) {
        String response;
        boolean isExit = false;
        try {
            Command c = Parser.parse(input);
            c.execute(tasks, ui, storage);
            isExit = c.isExit;
            if (isExit) {
                return ui.showGoodByeMessage();
            }
            return ui.printOutputGUI();
        } catch (DukeException e) {
            return ui.printException(e);
        }
    }

    /**
     * Main method to run Duke
     * @param args Argument values given when running the program
     */
    public static void main(String[] args) {
        new Duke().run();
    }

}
