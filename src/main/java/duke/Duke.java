package duke;

import duke.command.logic.ModuleCommand;

import duke.util.argparse4j.Argparse4jWrapper;
import duke.util.CcaList;
import duke.util.JsonWrapper;
import duke.util.ParserWrapper;
import duke.util.PlannerUi;
import duke.util.Reminder;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import duke.command.Command;
import duke.exceptions.planner.ModBadRequestStatus;
import duke.exceptions.ModException;
import duke.exceptions.planner.ModFailedJsonException;
import duke.exceptions.ModTimeIntervalTooCloseException;
import duke.modules.data.ModuleInfoDetailed;
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
    private CcaList ccas;
    private ParserWrapper parser;
    private Argparse4jWrapper argparser;
    private Reminder reminder;
    private JsonWrapper jsonWrapper;
    private PlannerUi modUi;
    private HashMap<String, ModuleInfoDetailed> modDetailedMap;
    private transient ByteArrayOutputStream output;

    /**
     * Constructor for Duke class.
     */
    public Duke(boolean gui) {
        store = new Storage();
        ui = new Ui();
        modUi = new PlannerUi();
        tasks = new TaskList(store);
        parser = new ParserWrapper();
        argparser = new Argparse4jWrapper();
        jsonWrapper = new JsonWrapper();
        modTasks = new ModuleTasksList();
        ccas = new CcaList();
        if (gui) {
            this.redirectOutput();
            ui.helloMsg();
        }
    }

    public Duke() {
        this(false);
    }

    /**
     * Redirect output for GUI compatibility.
     */
    private void redirectOutput() {
        this.output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(this.output));
        System.setErr(new PrintStream(this.output));
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

    private void modRunArgparse4j() {
        modUi.helloMsg();
        modSetup();
        while (true) {
            this.handleInput(modUi.readCommand());
        }
    }

    private void modRun() {
        modUi.helloMsg();
        modSetup();
        while (true) {
            try {
                String fullCommand = modUi.readCommand();
                modUi.showLine();
                ModuleCommand c = parser.parse(fullCommand, false);
                c.execute(modDetailedMap, modTasks, ccas, modUi, store, jsonWrapper);
            } catch (ModException e) {
                System.out.println(e.getMessage());
            } finally {
                modUi.showLine();
            }
        }
    }

    /**
     * gMain setup function to start threads in reminder and fill module data on startup.
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

    private void handleInput(String input) {
        try {
            modUi.showLine();
            ModuleCommand c = argparser.parseCommand(input);
            if (c != null) {
                c.execute(modDetailedMap, modTasks, ccas, modUi, store, jsonWrapper);
            }
        } catch (ModException e) {
            System.out.println(e.getMessage());
        } finally {
            modUi.showLine();
        }
    }

    /**
     * Get output from commands.
     * @param input user input
     * @return command output
     */
    public String getResponse(String input) {
        if (input != null) {
            this.output.reset();
            this.handleInput(input);
        }
        return this.output.toString();
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
        //duke.modRunArgparse4j();
    }
}
