package duke;

import duke.command.Command;
import duke.exceptions.DukeException;
import duke.exceptions.DukeTimeIntervalTooCloseException;
import duke.util.ParserWrapper;
import duke.util.RequestsData;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;
import duke.util.Reminder;

public class Duke {
    /**
     * Classes used for storage of data
     * Ui output and inputs and current
     * active tasks in TaskList and reminder.
     */
    private Storage store;
    private Ui ui;
    private TaskList tasks;
    private ParserWrapper parser;
    private Reminder reminder;
    private RequestsData data;

    private static Integer val = 0;

    /**
     * Constructor for Duke class.
     */
    public Duke() {
        store = new Storage();
        ui = new Ui();
        tasks = new TaskList(store);
        parser = new ParserWrapper();
    }

    /**
     * The main run loop for Duke, requesting for user input
     * and running valid commands. Invalid commands will be
     * alerted to users.
     */
    private void run() {
        ui.helloMsg();
        boolean isExit = false;
        // Starting reminder threads and pulling data from API
        try {
            reminder = new Reminder(tasks.getTasks());
            //reminder.run();
            data = new RequestsData();
        } catch (DukeTimeIntervalTooCloseException e) {
            System.out.println(e.getMessage());
        }
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                if (val != 0) {
                    data.getModJsonString(store, data.modsRequestSummaryBuilder());
                }
                ui.showLine();
                Command c = parser.parse(fullCommand);
                c.execute(tasks, ui, store, reminder);
                isExit = c.isExit();
                val++; // remove after testing
            } catch (DukeException e) {
                System.out.println(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Main entry point for Duke.
     * @param args Additional command line parameters, unused.
     */
    public static void main(String[] args) {
        Duke duke = new Duke();
        duke.run();
    }
}
