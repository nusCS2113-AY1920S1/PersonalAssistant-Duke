//@@author kyawtsan99

package planner.logic.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import planner.credential.user.User;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.modules.TaskList;
import planner.logic.modules.cca.Cca;
import planner.logic.modules.legacy.task.TaskWithMultiplePeriods;
import planner.logic.modules.module.ModuleTask;
import planner.logic.exceptions.planner.ModFailedJsonException;
import planner.logic.modules.module.ModuleInfoDetailed;
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

public class ShowCommandTest extends CommandTest {
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

    ShowCommandTest() throws ModException {
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

    @DisplayName("Show Module Output Test")
    @Test
    public void showModuleOutputShouldMatchExpectedOutput() {
        resetAll();
        execute(inputTasks);
        expectedOutput = "Got it, added the follow module!\n"
                +
                "[not taken] CG1111 | ModuleCode:CG1111, MC:6.0, SU: can S/U, grade: \n"
                +
                "Got it, added the follow module!\n"
                +
                "[not taken] LAJ1201 | ModuleCode:LAJ1201, MC:4.0, SU: can S/U, grade: \n"
                +
                "Got it, added the follow module!\n"
                +
                "[not taken] GES1012 | ModuleCode:GES1012, MC:4.0, SU: can S/U, grade: \n";
        assertEquals(expectedOutput, getOut());

        execute("show module\n");
        expectedOutput = "All modules in the list!\n"
                +
                "1. [not taken] CG1111 | ModuleCode:CG1111, MC:6.0, SU: can S/U, grade: \n"
                +
                "2. [not taken] LAJ1201 | ModuleCode:LAJ1201, MC:4.0, SU: can S/U, grade: \n"
                +
                "3. [not taken] GES1012 | ModuleCode:GES1012, MC:4.0, SU: can S/U, grade: \n";
        assertEquals(expectedOutput, getOut());

        execute("show core\n");
        expectedOutput = "Here is your list of core modules being added:\n"
                +
                "1. [not taken] CG1111 | ModuleCode:CG1111, MC:6.0, SU:true, grade: \n"
                +
                "\n"
                +
                "Number of core modules required to take for graduation:\n"
                +
                "21\n";

        execute("show ge\n");
        expectedOutput = "Here is your list of general education modules being added:\n"
                +
                "1. [not taken] GES1012 | ModuleCode:GES1012, MC:4.0, SU:true, grade: \n"
                +
                "\n"
                +
                "Number of general education modules required to take for graduation:\n"
                +
                "4\n";

        execute("show ue\n");
        expectedOutput = "Here is your list of unrestricted elective modules being added:\n"
                +
                "1. [not taken] LAJ1201 | ModuleCode:LAJ1201, MC:4.0, SU:true, grade: \n"
                +
                "\n"
                +
                "Number of unrestricted elective modules required to take for graduation:\n"
                +
                "7\n";
    }

}
