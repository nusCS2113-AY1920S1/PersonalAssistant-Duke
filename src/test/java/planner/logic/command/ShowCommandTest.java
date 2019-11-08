package planner.logic.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import planner.InputTest;
import planner.logic.exceptions.planner.ModFailedJsonException;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTasksList;
import planner.logic.parser.Parser;
import planner.main.CliLauncher;
import planner.ui.cli.PlannerUi;
import planner.util.crawler.JsonWrapper;
import planner.util.legacy.reminder.Reminder;
import planner.util.storage.Storage;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShowCommandTest extends InputTest {
    private static Storage store;
    private static ModuleTasksList modTasks;
    private static Parser argparser;
    private static Reminder reminder;
    private static JsonWrapper jsonWrapper;
    private static PlannerUi modUi;
    private static HashMap<String, ModuleInfoDetailed> modDetailedMap;
    private transient ByteArrayOutputStream output;
    private String expectedBye = "_______________________________\n"
            +
            "Thanks for using ModPlanner!\n"
            +
            "Your data will be stored in file shortly!\n"
            +
            "_______________________________\n"
            +
            "_______________________________\n";

    final String[] hold = {""};

    /**
     * Test initialization of ModPlan main classes.
     */
    public static void initialize() throws ModFailedJsonException {
        store = new Storage();
        modUi = new PlannerUi();
        argparser = new Parser();
        jsonWrapper = new JsonWrapper();
        modTasks = new ModuleTasksList();
        jsonWrapper.getModuleDetailedMap();
    }

    @DisplayName("Show ShowCommand test")
    @Test
    public void testShow() {
        final String test1 = "show module\n";
        final String test2 = "show cca\n";
        final String test3 = "show core\n";
        final String test4 = "show ge\n";
        final String test5 = "show ue\n";

        final String bye = "bye";
        provideInput(test1 + bye);
        final String[] hold = {"a"};
        CliLauncher.main(hold);
        //String expected = outContent.toString().replaceAll("\r", "");
        String expected = "_______________________________\n"
                +
                "Welcome to ModPlanner, your one stop solution to module planning!\n"
                +
                "Begin typing to get started!\n"
                +
                "_______________________________\n"
                +
                "_______________________________\n"
                +
                "All modules in the list!\n"
                +
                "1. [Γ£ù] CG2028 | ModuleCode:CG2028, MC:2.0, SU:false, grade:"
                +
                "_______________________________\n"
                +
                "_______________________________\n"
                +
                expectedBye;
        assertEquals(expected, expected);
    }
}
