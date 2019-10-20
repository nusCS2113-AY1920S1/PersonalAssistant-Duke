package planner;

import planner.command.logic.ModuleCommand;

import planner.util.argparse4j.Argparse4jWrapper;
import planner.util.CcaList;
import planner.util.JsonWrapper;
import planner.util.ParserWrapper;
import planner.util.PlannerUi;
import planner.util.Reminder;
import planner.util.Storage;
import planner.util.TaskList;
import planner.util.Ui;
import java.util.HashMap;

import planner.command.Command;
import planner.exceptions.planner.ModBadRequestStatus;
import planner.exceptions.ModException;
import planner.exceptions.planner.ModFailedJsonException;
import planner.exceptions.ModTimeIntervalTooCloseException;
import planner.modules.data.ModuleInfoDetailed;
import planner.util.commons.ModuleTasksList;

public class Planner {
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

    /**
     * Constructor for Duke class.
     */
    public Planner() {
        store = new Storage();
        ui = new Ui();
        modUi = new PlannerUi();
        tasks = new TaskList(store);
        parser = new ParserWrapper();
        argparser = new Argparse4jWrapper();
        jsonWrapper = new JsonWrapper();
        modTasks = new ModuleTasksList();
        ccas = new CcaList();
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
            try {
                String fullCommand = modUi.readCommand();
                modUi.showLine();
                ModuleCommand c = argparser.parseCommand(fullCommand);
                if (c != null) {
                    c.execute(modDetailedMap, modTasks, ccas, modUi, store, jsonWrapper);
                }
            } catch (ModException e) {
                System.out.println(e.getMessage());
            } finally {
                modUi.showLine();
            }
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
        Planner planner = new Planner();
        //duke.run();
        //duke.modRun();
        planner.modRunArgparse4j();
    }
}
