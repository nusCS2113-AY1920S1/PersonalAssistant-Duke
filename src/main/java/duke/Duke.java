package duke;

import exceptions.DukeException;
import parser.Parser;
import storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * The duke program runs a to-do application.
 * The program will first extracts existing tasks, if any, from duke.txt.
 * After which, it takes users' input and run the appropriate commands.
 */
public class Duke {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Creates a new Ui class that handles users' inputs.
     * Extracts duke.task from duke.Duke.txt or creates a new tasklist class.
     */
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

    /**
     * The function first prints welcome message.
     * After which, it takes in user's input and pass it to Parser class to excecute the approriate commands.
     */
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

    /**
     * Runs the main duke function.
     */
    public static void main(String[] args) throws DukeException {
        new Duke("data/duke.txt").run();
    }
}
