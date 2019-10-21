package planner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import planner.command.logic.ModuleCommand;
import planner.exceptions.ModException;
import planner.exceptions.planner.ModBadRequestStatus;
import planner.exceptions.planner.ModFailedJsonException;
import planner.modules.data.ModuleInfoDetailed;
import planner.util.CcaList;
import planner.util.JsonWrapper;
import planner.util.ParserWrapper;
import planner.util.PlannerUi;
import planner.util.Reminder;
import planner.util.Storage;
import planner.util.TaskList;
import planner.util.Ui;
import planner.util.argparse4j.Argparse4jWrapper;
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
    private transient ByteArrayOutputStream output;

    /**
     * Constructor for Duke class.
     */
    public Planner(boolean gui) {
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
            modUi.helloMsg();
        }
    }

    private Planner() {
        this(false);
    }

    /**
     * Redirect output for GUI compatibility.
     */
    private void redirectOutput() {
        this.output = new ByteArrayOutputStream();
        PrintStream printStreamGui = new PrintStream(this.output);
        System.setOut(printStreamGui);
        System.setErr(printStreamGui);
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
        Planner planner = new Planner();
        planner.modRunArgparse4j();
    }
}
