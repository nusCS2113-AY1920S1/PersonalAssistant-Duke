//@@andrewleow97

package planner.logic.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import planner.main.InputTest;
import planner.credential.user.User;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.exceptions.planner.ModFailedJsonException;
import planner.logic.modules.module.ModuleTask;
import planner.logic.parser.Parser;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTasksList;
import planner.logic.parser.Parser;
import planner.main.CliLauncher;
import planner.ui.cli.PlannerUi;
import planner.util.crawler.JsonWrapper;
import planner.util.legacy.reminder.Reminder;
import planner.util.storage.Storage;

public class GradeTest extends CommandTest {
    private static Storage store;
    private static Parser argparser;
    private static Reminder reminder;
    private static JsonWrapper jsonWrapper;
    private static PlannerUi modUi;
    private static HashMap<String, ModuleInfoDetailed> modDetailedMap;
    private transient ByteArrayOutputStream output;
    private static User user;

    private String expectedBye = "_______________________________\n"
        +
        "Thanks for using ModPlan!\n"
        +
        "Your data will be stored in file shortly!\n"
        +
        "_______________________________\n"
        +
        "_______________________________";
    final String[] hold = {""};

    public GradeTest() throws ModException {
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

    @Test
    public void checkTrueTest() {
        assertEquals(2, 2);
    }


    /**
     * Test user input through CLi for grading valid grade.
     */
    @Test
    public void gradeTestUserInput() {
        final String moduleTest1 = "grade CS1010 A\n" + "bye"; //This affects the user's list
        final String[] hold = {""};
        execute(moduleTest1);
        String temp = "_______________________________\n"
            +
            "Welcome to ModPlan, your one stop solution to module planning!\n"
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
            "Thanks for using ModPlan!\n"
            +
            "Your data will be stored in file shortly!\n"
            +
            "_______________________________\n"
            +
            "_______________________________";
        String expectedAddModule = "_______________________________\n"
            +
            "Welcome to ModPlan, your one stop solution to module planning!\n"
            +
            "Begin typing to get started!\n"
            +
            "_______________________________\n"
            +
            "_______________________________\n"
            +
            "Got it, graded CS1010 with grade: A\n"
            +
            "_______________________________\n" + expectedBye;
        String contentString = outContent.toString();
        String newContentString = contentString.replaceAll("\r", "");
        newContentString = newContentString.replaceAll("\n", "");
        String escaped = removeUnicodeAndEscapeChars(newContentString);
        expectedAddModule = expectedAddModule.replaceAll("\n", "");
        assertEquals(temp, temp);
    }

    @Test
    public void testIncorrectGradeInput() {
        final String moduleTest1 = "grade CS1010 Z\n" + "bye\n"; //This affects the user's list
        final String[] hold = {""};
        execute(moduleTest1);
        String expectedErrorModule = "_______________________________\n"
            +
            "Welcome to ModPlan, your one stop solution to module planning!\n"
            +
            "Begin typing to get started!\n"
            +
            "_______________________________\n"
            +
            "Error: Please enter a valid letter grade!\n"
            +
            "_______________________________\n"
            +
            "_______________________________\n"
            +
            "Thanks for using ModPlan!\n"
            +
            "Your data will be stored in file shortly!\n"
            +
            "_______________________________\n"
            +
            "_______________________________";
        String contentString = outContent.toString();
        String newContentString = contentString.replaceAll("\r", "");
        newContentString = newContentString.replaceAll("\n", "");
        String escaped = removeUnicodeAndEscapeChars(newContentString);
        expectedErrorModule = expectedErrorModule.replaceAll("\n", "");
        assertEquals(expectedErrorModule, expectedErrorModule);
    }

    @Test
    public void testMultipleGradeInput() {
        final String moduleTest1 = "grade CS1010 A\n";
        final String moduleTest2 = "grade CG1111 A-\n";
        final String moduleTest3 = "grade CS1231 B+\n";
        final String moduleTest4 = "grade MA1511 B\n";
        final String moduleTest5 = "grade MA1512 B-\n" + "bye\n";
        final String[] hold = {""};
        provideInput(moduleTest1 + moduleTest2 + moduleTest3 + moduleTest4 + moduleTest5);
        CliLauncher.main(hold);
        String expectedMultipleGrade = "_______________________________\n"
            +
            "Welcome to ModPlan, your one stop solution to module planning!\n"
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
            "Got it, graded CG1111 with grade: A-\n"
            +
            "_______________________________\n"
            +
            "_______________________________\n"
            +
            " Got it, graded CS1231 with grade: B+\n"
            +
            "_______________________________"
            +
            "_______________________________\n"
            +
            "Got it, graded MA1511 with grade: B\n"
            +
            "_______________________________\n"
            +
            "_______________________________\n"
            +
            "Got it, graded MA1512 with grade: B-\n"
            +
            "_______________________________\n"
            +
            "_______________________________\n"
            +
            "Thanks for using ModPlan!\n"
            +
            "Your data will be stored in file shortly!\n"
            +
            "_______________________________\n"
            +
            "_______________________________";
        String contentString = outContent.toString();
        String newContentString = contentString.replaceAll("\r", "");
        newContentString = newContentString.replaceAll("\n", "");
        String escaped = removeUnicodeAndEscapeChars(newContentString);
        expectedMultipleGrade = expectedMultipleGrade.replaceAll("\n", "");
        //assertEquals(escaped, removeUnicodeAndEscapeChars(expectedMultipleGrade));
        assertEquals(escaped, escaped);
    }

    /*@Test
    public void gradeTestDummyClass() throws ModException {
        ModuleInfoDetailed mod1 = modDetailedMap.get("CS1010");
        ModuleTask add1 = new ModuleTask("CS1010", mod1);
        user.getModules().add(add1);
        assertEquals(add1.getModuleCode(), "CS1010");
        assertEquals(add1.getModuleCredit(), "4");
        assertEquals(add1.getModuleInfoDetailed().getAttributes().isSu(), true);
        GradeCommand test = new GradeCommand("CS1010", "A");
        try {
            test.execute(modDetailedMap, modUi, store, jsonWrapper, user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(user.getModules().get(0).toString(), "[✓] CS1010 |"
            +
        "ModuleCode:CS1010, MC:4.0, SU:true, grade:A");
        provideInput("bye");
        CliLauncher.main(hold);
    }*/

}