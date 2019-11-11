//@@author kyawtsan99

package planner.logic.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import planner.main.InputTest;
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

public class ShowModule extends InputTest {
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
            "Thanks for using ModPlan!\n"
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

    @DisplayName("show module test")
    @Test
    public void testShow() {
        final String test = "show module\n";

        final String bye = "bye";
        provideInput(test + bye);
        final String[] hold = {""};
        CliLauncher.main(hold);
        String expectedShowModule = "_______________________________\n"
                +
                "Welcome to ModPlan, your one stop solution to module planning!\n"
                +
                "Begin typing to get started!\n"
                +
                "_______________________________\n"
                +
                "All modules in the list!\n"
                +
                "1. [✓] CS1010 | ModuleCode:CS1010, MC:4.0, SU:true, grade:A\n"
                +
                "2. [✗] GES1012 | ModuleCode:GES1012, MC:4.0, SU:true, grade:\n"
                +
                "3. [✗] LAJ1201 | ModuleCode:LAJ1201, MC:4.0, SU:true, grade:\n"
                +
                "_______________________________\n"
                +
                expectedBye;
        String contentString = outContent.toString();
        String expected = removeUnicodeAndEscapeChars(contentString);
        assertEquals(expected, expected);
    }
}
