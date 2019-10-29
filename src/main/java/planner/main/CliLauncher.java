package planner.main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineEvent;
import planner.logic.command.EndCommand;
import planner.logic.command.ModuleCommand;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.exceptions.planner.ModBadRequestStatus;
import planner.logic.exceptions.planner.ModFailedJsonException;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.cca.CcaList;
import planner.util.crawler.JsonWrapper;
import planner.ui.cli.PlannerUi;
import planner.util.legacy.reminder.Reminder;
import planner.util.logger.PlannerLogger;
import planner.util.storage.Storage;
import planner.logic.parser.Parser;
import planner.logic.modules.module.ModuleTasksList;

public class CliLauncher {
    /**
     * Classes used for storage of data
     * Ui output and inputs and current
     * active tasks in TaskList and reminder.
     */
    private Storage store;
    private ModuleTasksList modTasks;
    private CcaList ccas;
    private Parser argparser;
    private Reminder reminder;
    private JsonWrapper jsonWrapper;
    private PlannerUi modUi;
    private HashMap<String, ModuleInfoDetailed> modDetailedMap;
    private transient ByteArrayOutputStream output;

    private static final Logger logger = Logger.getLogger(CliLauncher.class.getName());

    /**
     * Constructor for Planner class.
     */
    public CliLauncher(boolean gui) {
        store = new Storage();
        modUi = new PlannerUi();
        argparser = new Parser();
        jsonWrapper = new JsonWrapper();
        modTasks = new ModuleTasksList();
        ccas = new CcaList();
        if (gui) {
            this.redirectOutput();
            modUi.helloMsg();
        }
    }

    private CliLauncher() {
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

    /**
     * Setup data files for module data and logging.
     */
    private void modSetup() {
        try {
            jsonWrapper.runRequests(store);
            modDetailedMap = jsonWrapper.getModuleDetailedMap();
            modTasks.setTasks(jsonWrapper.readJsonTaskList(store));
            PlannerLogger.setLogFile();
        } catch (ModBadRequestStatus e) {
            e.printStackTrace();
            PlannerLogger.log(e);
        } catch (ModFailedJsonException ej) {
            ej.getMessage();
            PlannerLogger.log(ej);
        } catch (IOException eio) {
            eio.getStackTrace();
            PlannerLogger.log(eio);
        }
    }

    private void modRunArgparse4j() {
        modUi.helloMsg();
        modSetup();
        boolean isExit = true;
        while (isExit) {
            isExit = this.handleInput(modUi.readCommand());
        }
    }

    private boolean handleInput(String input) {
        try {
            ModuleCommand c = argparser.parseCommand(input);
            if (c != null) {
                c.execute(modDetailedMap, modTasks, ccas, modUi, store, jsonWrapper);
                if (c instanceof EndCommand) {
                    return false;
                }
            }
        } catch (ModException e) {
            System.out.println(e.getMessage());
            PlannerLogger.log(e);
        } finally {
            modUi.showLine();
        }
        return true;
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
        CliLauncher planner = new CliLauncher();
        planner.modRunArgparse4j();
    }
}
