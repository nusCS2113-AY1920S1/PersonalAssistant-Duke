package planner.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import planner.credential.user.User;
import planner.logic.command.EndCommand;
import planner.logic.command.ModuleCommand;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.exceptions.planner.ModFailedJsonException;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.parser.Parser;
import planner.ui.cli.PlannerUi;
import planner.ui.gui.MainWindow;
import planner.util.crawler.JsonWrapper;
import planner.util.logger.PlannerLogger;
import planner.util.storage.Storage;

public class CliLauncher {
    /**
     * Classes used for storage of data
     * Ui output and inputs and current
     * active tasks in TaskList and reminder.
     */
    private Storage store;
    private Parser argparser;
    private JsonWrapper jsonWrapper;
    private PlannerUi modUi;
    private HashMap<String, ModuleInfoDetailed> modDetailedMap;
    private User profile;
    public static List<Timer> timerPool = new ArrayList<>();
    private boolean isAlive;

    /**
     * Constructor for Planner class.
     */
    public CliLauncher(MainWindow mainWindow) {
        store = new Storage();
        modUi = new PlannerUi(mainWindow);
        argparser = new Parser();
        jsonWrapper = new JsonWrapper();
        isAlive = true;
    }

    public CliLauncher() {
        this(null);
    }

    /**
     * Setup data files for module data and logging.
     */
    public void setup() {
        try {
            modDetailedMap = jsonWrapper.getModuleDetailedMap(true, store);
            //modTasks.setTasks(jsonWrapper.readJsonTaskList(store));
            profile = User.loadProfile(modDetailedMap, modUi, store, jsonWrapper);
            profile.setSemester(0);
            PlannerLogger.setLogFile(store);
        } catch (ModFailedJsonException ej) {
            ej.getMessage();
            PlannerLogger.log(ej);
        } catch (IOException eio) {
            eio.getStackTrace();
            PlannerLogger.log(eio);
        }
    }

    /**
     * Run Planner.
     */
    public void run() {
        modUi.helloMsg();
        while (isAlive) {
            isAlive = this.handleInput();
        }
        modUi.updateGui();
    }

    /**
     * Handle user input.
     * @return false if EndCommand is reached, else true
     */
    public boolean handleInput() {
        try {
            String input = modUi.readInput();
            if (input == null) {
                return true;
            }
            ModuleCommand c = argparser.parseCommand(input);
            if (c != null) {
                c.call(modDetailedMap, modUi, store, jsonWrapper, profile);
                if (c instanceof EndCommand) {
                    return false;
                }
            }
        } catch (ModException e) {
            modUi.println(e.getMessage());
            PlannerLogger.log(e);
        } finally {
            modUi.showLine();
        }
        return true;
    }

    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Main entry point for Duke.
     * @param args Additional command line parameters, unused.
     */
    public static void main(String[] args) {
        User.restoreDefaultPath();
        //TODO: args flag could be passed into program for optional runs
        CliLauncher planner = new CliLauncher();
        planner.setup();
        planner.run();
    }
}
