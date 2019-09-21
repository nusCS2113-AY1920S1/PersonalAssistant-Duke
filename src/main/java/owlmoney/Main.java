package owlmoney;

import java.io.FileNotFoundException;

import owlmoney.logic.command.Command;
import owlmoney.logic.exception.DukeException;
import owlmoney.logic.parser.Parser;
import owlmoney.storage.Storage;
import owlmoney.model.task.TaskList;
import owlmoney.ui.Ui;

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

