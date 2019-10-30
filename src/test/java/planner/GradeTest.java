package planner;

import org.junit.jupiter.api.Test;
import planner.logic.command.Arguments;
import planner.logic.command.GradeCommand;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.parser.Parser;
import planner.logic.command.SearchThenAddCommand;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTasksList;
import planner.logic.modules.module.ModuleTask;
import planner.main.CliLauncher;
import planner.ui.cli.PlannerUi;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GradeTest extends InputTest {

    CliLauncher modPlan = new CliLauncher(false);


    @Test
    public void GradeTest() {
        final String moduleTest1 = "grade CS1010 A";
        final String[] hold = {""};
        setUpStreams();
        provideInput(moduleTest1);
        modPlan.main(hold);
        restoreStreams();
        provideInput(moduleTest1);
        String expectedAddModule = "_______________________________\n" +
            "Welcome to ModPlanner, your one stop solution to module planning!\n" +
            "Begin typing to get started!\n" +
            "_______________________________\n" +
            "Got it, added the follow module!\n" +
            "[✓] CS1010 | ModuleCode:CS1010, MC:4.0, SU:true, grade:A\n" +
            "_______________________________\n";
        assertEquals(expectedAddModule, outContent.toString());
        /*provideInput("show module");
        String expectedList = "_______________________________\n" +
            "All modules in the list!\n" +
            "1 [✓] CS1010 | ModuleCode:CS1010, MC:4.0, SU:true, grade:A\n" +
            "_______________________________";
        assertEquals(expectedList, outContent.toString().replace("\r", ""));*/
    }



}
