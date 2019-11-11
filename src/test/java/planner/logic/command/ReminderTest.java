//@@author kyawtsan99

package planner.logic.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import planner.credential.user.User;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.exceptions.planner.ModFailedJsonException;
import planner.logic.modules.TaskList;
import planner.logic.modules.cca.Cca;
import planner.logic.modules.legacy.task.TaskWithMultiplePeriods;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTask;
import planner.logic.modules.module.ModuleTasksList;
import planner.logic.parser.Parser;
import planner.main.CliLauncher;
import planner.ui.cli.PlannerUi;
import planner.util.crawler.JsonWrapper;
import planner.util.legacy.reminder.Reminder;
import planner.util.storage.Storage;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReminderTest extends CommandTest {
    private static Storage store;
    private static ModuleTasksList modTasks;
    private static Parser argparser;
    private static Reminder reminder;
    private static JsonWrapper jsonWrapper;
    private static PlannerUi modUi;
    private static User user;
    private static List<TaskWithMultiplePeriods> ccas;
    private static HashMap<String, ModuleInfoDetailed> modDetailedMap;
    private transient ByteArrayOutputStream output;

    private static final TaskList<Cca> emptyCcaList = new TaskList<>();
    private static final TaskList<ModuleTask> emptyModuleList = new TaskList<>();
    private String expectedOutput;
    private String inputTasks = "add module CG1111 \n"
            +
            "add module LAJ1201\n"
            +
            "add module GES1012";

    ReminderTest() throws ModException {
        super();
    }

    /**
     * Test initialization of ModPlan main classes.
     */
    public static void initialize() throws ModFailedJsonException {
        store = new Storage();
        modUi = new PlannerUi();
        argparser = new Parser();
        jsonWrapper = new JsonWrapper();
        jsonWrapper.getModuleDetailedMap();
        user = new User();
    }

    /**
     * Stops the reminder message.
     */
    private void killAllTimers() {
        for (Timer timer: CliLauncher.timerPool) {
            timer.cancel();
        }
    }

    @DisplayName("Reminder Message Output Test")
    @Test
    public void reminderMessageOutputShouldMatchExpectedOutput() {
        resetAll();

        execute("reminder list");
        expectedOutput = "_______________________________\n"
                +
                "Would you like to set your reminder to every:\n"
                +
                "1) for 10 seconds\n"
                +
                "2) for 30 seconds\n"
                +
                "3) for 1 minute\n"
                +
                "4) for 2 minutes\n"
                +
                "*helpline* : for 1), enter 'reminder one'"
                +
                "\n";
        assertEquals(expectedOutput, getOut());

        /*execute("reminder one");
        expectedOutput = "_______________________________\n"
                +
                "Please remember to update your module information!\n"
                +
                "To do so, you can input the update command in the following format:\n"
                +
                "update module"
                +
                "\n";
        assertEquals(expectedOutput, getOut());
        killAllTimers();
        resetAll();

        execute("reminder two");
        expectedOutput = "_______________________________\n"
                +
                "Please remember to update your module information!\n"
                +
                "To do so, you can input the update command in the following format:\n"
                +
                "update module"
                +
                "\n";
        assertEquals(expectedOutput, getOut());
        killAllTimers();
        resetAll();

        execute("reminder three");
        expectedOutput = "_______________________________\n"
                +
                "Please remember to update your module information!\n"
                +
                "To do so, you can input the update command in the following format:\n"
                +
                "update module"
                +
                "\n";
        assertEquals(expectedOutput, getOut());
        killAllTimers();
        resetAll();

        execute("reminder four");
        expectedOutput = "_______________________________\n"
                +
                "Please remember to update your module information!\n"
                +
                "To do so, you can input the update command in the following format:\n"
                +
                "update module"
                +
                "\n";
        assertEquals(expectedOutput, getOut());
        killAllTimers();
        resetAll();*/

        execute("reminder stop");
        expectedOutput = "_______________________________\n"
                +
                "Your reminder for the update is being stopped.\n"
                +
                "To activate the reminder again, type reminder list."
                +
                "\n";
        assertEquals(expectedOutput, getOut());
    }
}
