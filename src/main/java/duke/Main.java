package duke;

import java.io.FileNotFoundException;

import duke.command.Command;
import duke.exception.DukeException;
import duke.parse.Parser;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * The main class.
 */

class Main {

    private Ui ui;
    private Parser parser;
    private TaskList tasks;
    private Storage storage;

    /**
     * Initializes a new Duke session.
     */
    private Main() {
        ui = new Ui();
        parser = new Parser();
        storage = new Storage("data/tasks.txt");
        try {
            tasks = new TaskList(storage.readFile());
        } catch (FileNotFoundException e) {
            ui.printError("Could not read tasks from disk, will start with empty file");
            tasks = new TaskList();
        }
    }

    /**
     * Starts up the initialized Duke session.
     */
    private void run() {
        boolean hasExited = false;
        ui.greet();
        while (!hasExited && parser.hasNextLine()) {
            try {
                Command command = parser.parseLine();
                command.execute(tasks, ui, storage);
                hasExited = command.isExit();
            } catch (DukeException exceptionMessage) {
                ui.printError(exceptionMessage.toString());
            }
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}

