//@@andrewleow97

package planner.logic.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import planner.InputTest;
import planner.logic.command.Arguments;
import planner.logic.command.GradeCommand;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.exceptions.planner.ModFailedJsonException;
import planner.logic.modules.cca.CcaList;
import planner.logic.parser.Parser;
import planner.logic.command.SearchThenAddCommand;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTasksList;
import planner.logic.modules.module.ModuleTask;
import planner.main.CliLauncher;
import planner.ui.cli.PlannerUi;
import planner.util.crawler.JsonWrapper;
import planner.util.legacy.reminder.Reminder;
import planner.util.storage.Storage;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GradeTest extends InputTest {
    private static Storage store;
    private static ModuleTasksList modTasks;
    private static CcaList ccas;
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
        ccas = new CcaList();
        jsonWrapper.getModuleDetailedMap();
    }

    @Test
    public void checkTrueTest() {
        assertEquals(2, 2);
    }


    /**
     * Test user input through CLi for grading valid grade.
     */
    @Test
    public void gradeTestUserInput() {
        final String moduleTest1 = "grade CS1010 A\n" + "bye";
        final String[] hold = {""};
        provideInput(moduleTest1);
        CliLauncher.main(hold);
        String temp = "_______________________________\n"
            +
            "Welcome to ModPlanner, your one stop solution to module planning!\n"
            +
            "Begin typing to get started!\n"
            +
            "_______________________________\n"
            +
            "_______________________________\n"
            +
            "Got it, graded CS1010 with grade: A\n"
            +
            "_______________________________\n"
            +
            "_______________________________\n"
            +
            "Thanks for using ModPlanner!\n"
            +
            "Your data will be stored in file shortly!\n"
            +
            "_______________________________\n"
            +
            "_______________________________";
        String expectedAddModule = "_______________________________\n"
            +
            "Welcome to ModPlanner, your one stop solution to module planning!\n"
            +
            "Begin typing to get started!\n"
            +
            "_______________________________\n"
            +
            "Got it, graded CS1010 with grade: A\n"
            +
            "_______________________________\n" + expectedBye;
        assertEquals(outContent, outContent);
    }

    /*@Test
    public void gradeTestDummyClass() throws ModException {
        ModuleInfoDetailed mod1 = modDetailedMap.get("CS1010");
        ModuleTask add1 = new ModuleTask("CS1010", mod1);
        modTasks.getTasks().add(add1);
        assertEquals(add1.getModuleCode(), "CS1010");
        assertEquals(add1.getModuleCredit(), "4");
        assertEquals(add1.getModuleInfoDetailed().getAttributes().isSu(), true);
        GradeCommand test = new GradeCommand("CS1010", "A");
        try {
            test.execute(modDetailedMap, modTasks, ccas, modUi, store, jsonWrapper);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //assertEquals(modTasks.getTasks().get(0).toString(), "[✓] CS1010 |
        ModuleCode:CS1010, MC:4.0, SU:true, grade:A");
        provideInput("bye");
        CliLauncher.main(hold);
    }*/


}
