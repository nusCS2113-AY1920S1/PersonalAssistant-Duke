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

public class ShowGeTest extends InputTest {
    private String expectedBye = "_______________________________\n"
            +
            "Thanks for using ModPlanner!\n"
            +
            "Your data will be stored in file shortly!\n"
            +
            "_______________________________\n"
            +
            "_______________________________\n";
    private String inputModule = "add module ges1012";

    @DisplayName("show ge test")
    @Test
    public void testShow() {
        final String test = "show ge\n";

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
                "Here is your list of general education modules being added:\n"
                +
                "1. [✗] GES1012 | ModuleCode:GES1012, MC:4.0, SU:true, grade:"
                +
                "\n"
                +
                "Number of general education modules required to take for graduation:\n"
                +
                "4\n"
                +
                "_______________________________\n"
                +
                expectedBye;
        assertEquals(expectedShowModule, getReplace());
    }
}
