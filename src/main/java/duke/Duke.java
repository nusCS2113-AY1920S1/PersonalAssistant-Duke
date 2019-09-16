package duke;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.storage.Storage;
import duke.tasks.Schedule;
import duke.tasks.TaskList;
import duke.ui.Ui;
import duke.parsers.Parser;

import java.util.Scanner;

/**
 * Duke is a public class that contains the main function to drive the program
 * It encapsulates a Storage object, a TaskList object, an Ui object and a Schedule object
 */
public class Duke {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Schedule schedule;
    private Scanner in = new Scanner(System.in);

    /**
     * This is a constructor of Duke to start the program
     */
    public Duke() {
        ui = new Ui();
        storage = new Storage();
        schedule = new Schedule();
        try {
            tasks = new TaskList(storage.load(schedule));
        } catch (DukeException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     *  Run is a function that generate the flow of duke program from beginning until the end
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand(in);
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage, schedule);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * This is the main function
     * @param args
     */
    public static void main(String[] args) {
        new Duke().run();
    }
}














