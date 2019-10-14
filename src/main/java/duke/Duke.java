package duke;

import duke.command.logic.ModuleCommand;
import duke.util.PlannerUi;

import java.util.HashMap;

import duke.command.Command;
import duke.exceptions.planner.ModBadRequestStatus;
import duke.exceptions.ModException;
import duke.exceptions.planner.ModFailedJsonException;
import duke.exceptions.ModTimeIntervalTooCloseException;
import duke.modules.data.ModuleInfoDetailed;
import duke.util.JsonWrapper;
import duke.util.ParserWrapper;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;
import duke.util.Reminder;
import duke.util.commons.ModuleTasksList;

public class Duke {
    /**
     * Classes used for storage of data
     * Ui output and inputs and current
     * active tasks in TaskList and reminder.
     */
    private Storage store;
    private Ui ui;
    private TaskList tasks;
    private ModuleTasksList modTasks;
    private ParserWrapper parser;
    private Reminder reminder;
    private JsonWrapper jsonWrapper;
    private PlannerUi modUi;
    private HashMap<String, ModuleInfoDetailed> modDetailedMap;

    /**
     * Constructor for Duke class.
     */
    public Duke() {
        store = new Storage();
        ui = new Ui();
        modUi = new PlannerUi();
        tasks = new TaskList(store);
        parser = new ParserWrapper();
        jsonWrapper = new JsonWrapper();
        modTasks = new ModuleTasksList();
    }

    private void modSetup() {
        try {
            jsonWrapper.runRequests(store);
            modDetailedMap = jsonWrapper.getModuleDetailedMap();
            modTasks.setTasks(jsonWrapper.readJsonTaskList(store));
        } catch (ModBadRequestStatus er) {
            er.printStackTrace();
        } catch (ModFailedJsonException ej) {
            System.out.println(ej.getLocalizedMessage());
        }
    }

    private void modRun() {
        modUi.helloMsg();
        modSetup();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = modUi.readCommand();
                modUi.showLine();
                ModuleCommand c = parser.parse(fullCommand, false);
                c.execute(modDetailedMap, modTasks, modUi, store, jsonWrapper);
                isExit = c.isExit();
            } catch (ModException e) {
                System.out.println(e.getMessage());
            } finally {
                modUi.showLine();
            }
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
            jsonWrapper.runRequests(store);
            modDetailedMap = jsonWrapper.getModuleDetailedMap();
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
                isExit = c.isExit();
            } catch (ModException e) {
                e.printStackTrace();
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
        //duke.run();
        duke.modRun();
    }
}
