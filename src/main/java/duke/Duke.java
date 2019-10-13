package duke;

import java.util.Arrays;
import java.util.HashMap;

import duke.command.Command;
import duke.command.ListCommand;
import duke.exceptions.ModBadRequestStatus;
import duke.exceptions.ModException;
import duke.exceptions.ModFailedJsonException;
import duke.exceptions.ModTimeIntervalTooCloseException;
import duke.modules.ModuleInfoDetailed;
import duke.modules.ModuleInfoSummary;
import duke.util.JsonWrapper;
import duke.util.ParserWrapper;
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
    private JsonWrapper data;
    private HashMap<String, ModuleInfoSummary> modSummaryMap;
    private HashMap<String, ModuleInfoDetailed> modDetailedMap;

    /**
     * Constructor for Duke class.
     */
    public Duke() {
        store = new Storage();
        ui = new Ui();
        tasks = new TaskList(store);
        parser = new ParserWrapper();
        data = new JsonWrapper();
    }

    //TODO: function to be removed after implementing feature
    /**
     * Testing function for json parsing for both summary and detailed json files.
     */
    private void testJson(Command c) {
        if (c instanceof ListCommand) {
            // Demo test of commands
            System.out.println(modSummaryMap.get("CS2101"));
            System.out.println(modDetailedMap.get("CS2101"));
            System.out.println(modDetailedMap.get("CS2101").getAttributes().isSu());
            System.out.println(Arrays.toString(modSummaryMap.get("CS2113T").getSemesters()));
            System.out.println(modSummaryMap.get("CG2028").getTitle());
            System.out.println(modSummaryMap.get("CS1010"));
        }
    }


    /**
     * Main setup function to start threads in reminder and fill module data on startup.
     */
    private void setup() {
        try {
            // Starting reminder threads and pulling data from API
            reminder = new Reminder(tasks.getTasks());
            reminder.run();
            // This pulls data once and stores in the data files.
            data.runRequests(store);
            modSummaryMap = data.getModuleSummaryMap();
            modDetailedMap = data.getModuleDetailedMap();
        } catch (ModTimeIntervalTooCloseException e) {
            System.out.println(e.getMessage());
        } catch (ModBadRequestStatus er) {
            er.printStackTrace();
        } catch (ModFailedJsonException ej) {
            System.out.println(ej.getLocalizedMessage());
        }
    }

    /**
     * The main run loop for Duke, requesting for user input
     * and running valid commands. Invalid commands will be
     * alerted to users.
     */
    private void run() {
        ui.helloMsg();
        setup();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = parser.parse(fullCommand);
                c.execute(tasks, ui, store, reminder);
                testJson(c);
                isExit = c.isExit();
            } catch (ModException e) {
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
        //TODO: args flag could be passed into program for optional runs
        Duke duke = new Duke();
        duke.run();
    }
}
