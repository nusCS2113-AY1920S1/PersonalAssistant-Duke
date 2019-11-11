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

public class ShowUeTest extends InputTest {
    private String expectedBye = "_______________________________\n"
            +
            "Thanks for using ModPlanner!\n"
            +
            "Your data will be stored in file shortly!\n"
            +
            "_______________________________\n"
            +
            "_______________________________\n";

    @DisplayName("show ue test")
    @Test
    public void testShow() {
        final String test = "show ue\n";

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
                "Here is your list of unrestricted elective modules being added:\n"
                +
                "1. [✗] LAJ1201 | ModuleCode:LAJ1201, MC:4.0, SU:true, grade:"
                +
                "\n"
                +
                "Number of unrestricted elective modules required to take for graduation:\n"
                +
                "7\n"
                +
                "_______________________________\n"
                +
                expectedBye;
        assertEquals(expectedShowModule, getReplace());
    }
}
