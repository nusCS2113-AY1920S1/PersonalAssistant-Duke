//@@author e0313687

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
import planner.ui.cli.PlannerUi;
import planner.util.crawler.JsonWrapper;
import planner.util.legacy.reminder.Reminder;
import planner.util.storage.Storage;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SortCcaTest extends CommandTest {
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
    private String inputTasks = "add cca soccer --begin 3 --end 5 --dayOfWeek Monday\n"
            +
            "add cca dance --begin 7 --end 9 --dayOfWeek Monday\n"
            +
            "add module CG1111 --begin 3 --end 5 --dayOfWeek Wednesday\n"
            +
            "scheduleCca 1 --begin 3 --end 5 --dayOfWeek Thursday";

    SortCcaTest() throws ModException {
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
        jsonWrapper.getModuleDetailedMap(true, store);
        user = new User();
    }

    @DisplayName("Sort Cca Output Test")
    @Test
    public void sortCcaOutputShouldMatchExpectedOutput() {
        resetAll();
        execute(inputTasks);
        expectedOutput = "Got it, added the follow cca!\n"
                + "[C] soccer | 03:00 - 05:00 on MONDAY\n"
                + "Got it, added the follow cca!\n"
                + "[C] dance | 07:00 - 09:00 on MONDAY\n"
                + "Got it, added the follow module!\n"
                + "[not taken] CG1111 | ModuleCode:CG1111, MC:6.0, SU: can S/U, grade: "
                + "| 03:00 - 05:00 on WEDNESDAY\n"
                + "Got it, added the follow cca!\n"
                + "[C] soccer | 03:00 - 05:00 on MONDAY, 03:00 - 05:00 on THURSDAY\n";
        assertEquals(expectedOutput, getOut());

        execute("sort cca\n");
        expectedOutput = "Here are your sorted cca:\n"
                + "_______________________________\n"
                + "[C] dance | 07:00 - 09:00 on MONDAY\n"
                + "[C] soccer | 03:00 - 05:00 on MONDAY, 03:00 - 05:00 on THURSDAY\n";
        assertEquals(expectedOutput, getOut());

        execute("sort cca --r\n");
        expectedOutput = "Here are your sorted cca:\n"
                + "_______________________________\n"
                + "[C] soccer | 03:00 - 05:00 on MONDAY, 03:00 - 05:00 on THURSDAY\n"
                + "[C] dance | 07:00 - 09:00 on MONDAY\n";
        assertEquals(expectedOutput, getOut());
    }
}
