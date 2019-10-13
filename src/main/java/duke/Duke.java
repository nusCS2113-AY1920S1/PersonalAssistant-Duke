package duke;

import duke.command.logic.ModuleCommand;
import duke.util.PlannerUi;
import java.util.Arrays;
import java.util.HashMap;

import duke.command.Command;
import duke.command.ListCommand;
import duke.exceptions.ModBadRequestStatus;
import duke.exceptions.ModException;
import duke.exceptions.ModFailedJsonException;
import duke.exceptions.ModTimeIntervalTooCloseException;
import duke.modules.data.ModuleInfoDetailed;
import duke.modules.data.ModuleInfoSummary;
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
    private PlannerUi modUi;
    private HashMap<String, ModuleInfoSummary> modSummaryMap;
    private HashMap<String, ModuleInfoDetailed> modDetailedMap;

    private boolean mode = true;


    /**
     * Constructor for Duke class.
     */
    public Duke() {
        store = new Storage();
        ui = new Ui();
        modUi = new PlannerUi();
        tasks = new TaskList(store);
        parser = new ParserWrapper();
        data = new JsonWrapper();
    }

    public Duke(boolean mode) {
        this();
        this.mode = mode;
    }

    //TODO: function to be removed after implementing feature
    /**
     * Testing function for json parsing for both summary and detailed json files.
     */
    private void testJson(Command c) {
        if (c instanceof ListCommand) {
            this.testJson();
        }
    }

    private void testJson() {
        // Demo test of commands
        System.out.println(modSummaryMap.get("CS2101"));
        System.out.println(modDetailedMap.get("CS2101"));
        System.out.println(modDetailedMap.get("CS2101").getAttributes().isSu());
        System.out.println(Arrays.toString(modSummaryMap.get("CS2113T").getSemesters()));
        System.out.println(modSummaryMap.get("CG2028").getTitle());
        System.out.println(modSummaryMap.get("CS1010"));
    }

    private void modSetup() {
        try {
            data.runRequests(store);
            modSummaryMap = data.getModuleSummaryMap();
            modDetailedMap = data.getModuleDetailedMap();
        } catch (ModBadRequestStatus er) {
            er.printStackTrace();
        } catch (ModFailedJsonException ej) {
            System.out.println(ej.getLocalizedMessage());
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

    private void modRun(boolean mode) {
        modUi.helloMsg();
        modSetup();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = modUi.readCommand();
                modUi.showLine();
                ModuleCommand c = parser.parse(fullCommand, mode);
                c.execute(modSummaryMap, modDetailedMap, modUi, store);
                testJson();
                isExit = c.isExit();
            } catch (ModException e) {
                System.out.println(e.getMessage());
            } finally {
                modUi.showLine();
            }
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
