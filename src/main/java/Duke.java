/**
 * This is the main entrance of Duke programme.
 *
 * @author tygq13
 */
import exception.*;
import task.TaskList;
import ui.Ui;
import util.Parser;
import util.Storage;
import command.Command;

public class Duke {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Duke constructor with filePath.
     *
     * @param filePath the file path where duke data is stored.
     */
    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the Duke programme by receiving user commands and executing the commands.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                isExit = c.isExit();
                c.execute(tasks, ui, storage);
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Initializes new Duke user and runs the programme.
     * @param args programme arguments.
     */
    public static void main(String[] args) {
        //todo: allow user to specify data path
        new Duke("D:\\codes\\java\\duke\\data").run();
    }
}
