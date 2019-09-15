import storage.Storage;
import ui.Ui;
import task.TaskList;
import exception.DukeException;
import command.Command;
import parser.Parser;

/**
 * Represents our Duke and contains the main program of Duke.
 */
public class Duke {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs the Duke with the filePath of storage.txt
     *
     * @param filePath The filePath of storage.txt
     */
    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (DukeException e) {
            ui.showError(e);
            tasks = new TaskList();
        }
    }

    /**
     * Runs the Duke.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            String fullCommand = ui.readCommand();
            try {
                Command packagedCommand = Parser.parse(fullCommand);
                packagedCommand.execute(tasks, ui, storage);
                isExit = packagedCommand.isExit();
            } catch (DukeException e) {
                ui.showError(e);
            }
        }
    }

    /**
     * Runs the main program of the Duke.
     *
     * @param args necessary arguments demanded by the main method.
     */
    public static void main(String[] args) {
        new Duke(System.getProperty("user.dir") + "/data/TaskListStorage.txt").run();
    }

}
