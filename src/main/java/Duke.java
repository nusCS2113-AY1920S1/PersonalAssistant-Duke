import exceptions.DukeException;
import parser.Parser;
import storage.Storage;
import task.*;
import ui.Ui;

/**
 * Runs the duke program where it first extracts tasks from duke.txt.
 * After which, it takes in input from users and run the appropriate commands.
 */
public class Duke {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.getTasksFromDatabase());
        } catch (DukeException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() throws DukeException {
        ui.showWelcome();
        System.out.flush();
        while (true) {
            try {
                String command = ui.readInput();
                String[] arr = command.split(" ", 2);
                String firstWord = arr[0];
                Parser parser = new Parser();
                int check = parser.handleCommand(firstWord, command);
                if (check == 1) {
                    break;
                }
            } catch (NullPointerException e) {
                throw DukeException.INPUT_NOT_FOUND;
            }
        }
    }

    public static void main(String[] args) throws DukeException {
        new Duke("data/duke.txt").run();
    }
}





