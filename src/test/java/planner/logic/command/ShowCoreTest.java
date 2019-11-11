//@@author kyawtsan99

package planner.logic.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

public class ShowCoreTest extends InputTest {
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

    @DisplayName("show core test")
    @Test
    public void testShow() {
        final String test = "show core\n";

        final String bye = "bye";
        provideInput(test + bye);
        final String[] hold = {""};
        CliLauncher.main(hold);
        String expectedShowModule = "_______________________________\n"
                +
                "Welcome to ModPlanner, your one stop solution to module planning!\n"
                +
                "Begin typing to get started!\n"
                +
                "_______________________________\n"
                +
                "Here is your list of core modules being added:\n"
                +
                "1. [✓] CS1010 | ModuleCode:CS1010, MC:4.0, SU:true, grade:A"
                +
                "\n"
                +
                "Number of core modules required to take for graduation:\n"
                +
                "21\n"
                +
                "_______________________________\n"
                +
                expectedBye;
        assertEquals(expectedShowModule, getReplace());
    }
}
