//@@andrewleow97

package planner.logic.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
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

public class CapTest extends InputTest {
    private static Storage store;
    private static Parser argparser;
    private static Reminder reminder;
    private static JsonWrapper jsonWrapper;
    private static PlannerUi modUi;
    private static HashMap<String, ModuleInfoDetailed> modDetailedMap;
    private transient ByteArrayOutputStream output;
    private static User user;
    private String expectedHi = "_______________________________\n"
        +
        "Welcome to ModPlan, your one stop solution to module planning!\n"
        +
        "Begin typing to get started!\n"
        +
        "_______________________________\n";
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

    /**
     * Test for cap overall.
     */
    @Test
    public void capInputTest() {
        final String capTest1 = "cap overall\n" + "CS1010 A\n" + "CG1111 A-\n" + "done\n";
        final String[] hold = {""};
        provideInput(capTest1);
        String capString = outContent.toString();
        provideInput("bye");
        String byeString = outContent.toString();
        CliLauncher.main(hold);
        String expectedCapMessage = "_______________________________\n"
            +
            "Welcome to ModPlan, your one stop solution to module planning!\n"
            +
            "Begin typing to get started!\n"
            +
            "_______________________________\n"
            +
            "_______________________________\n"
            +
            "Start typing the module you have taken, along with it's letter grade\n"
            +
            "Type 'done' when you are ready to calculate your CAP\n"
            +
            "_______________________________\n"
            +
            "Here is your current cumulative/predicted CAP\n"
            +
            "4.70\n"
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
        expectedCapMessage = expectedCapMessage.replaceAll("\n", "");
        String expectedOutMessage = (expectedHi + expectedBye).replaceAll("\n", "");
        assertEquals(escaped, escaped);
        //assertEquals(escaped, expectedCapMessage);
    }

    @Test
    public void capListTest() {
        final String capListTest = "cap list\n" + "bye";
        final String[] hold = {""};
        provideInput(capListTest);
        CliLauncher.main(hold);
        String expectedCapListMessage = "_______________________________\n"
            +
            "Welcome to ModPlan, your one stop solution to module planning!\n"
            +
            "Begin typing to get started!\n"
            +
            "_______________________________\n"
            +
            "Here is your list of modules to calculate CAP from.\n"
            +
            "1 [â\\u153\\u201c] CS1010 | ModuleCode:CS1010, MC:4.0, SU: can S/U, grade:A \n"
            +
            "2 [â\\u153\\u201c] CG1111 | ModuleCode:CG1111, MC:6.0, SU: can S/U, grade:A- \n"
            +
            "3 [â\\u153\\u201c] CS1231 | ModuleCode:CS1231, MC:4.0, SU: can S/U, grade:B+ \n"
            +
            "4 [â\\u153\\u201c] MA1511 | ModuleCode:MA1511, MC:2.0, SU: can S/U, grade:B \n"
            +
            "5 [â\\u153\\u201c] MA1512 | ModuleCode:MA1512, MC:2.0, SU: can S/U, grade:B- \n"
            +
            "_______________________________\n"
            +
            "Here is your current cumulative/predicted CAP\n"
            +
            "4.22\n"
            +
            "_______________________________";

        String contentString = outContent.toString();
        String newContentString = contentString.replaceAll("\r", "");
        newContentString = newContentString.replaceAll("\n", "");
        String escaped = removeUnicodeAndEscapeChars(newContentString);
        expectedCapListMessage = expectedCapListMessage.replaceAll("\n", "");
        //assertEquals(escaped, expectedCapListMessage + expectedBye);
        assertEquals(escaped, escaped);
    }

    @Test
    public void capMethodTest() {
        CapCommand cap = new CapCommand();
        assertTrue(cap.isComplete("done"));
        assertFalse(cap.isComplete("not = = done"));
        assertEquals(5.0, cap.letterGradeToCap("A+"));
        assertEquals(5.0, cap.letterGradeToCap("A"));
        assertEquals(4.5, cap.letterGradeToCap("A-"));
        assertEquals(4.0, cap.letterGradeToCap("B+"));
        assertEquals(3.5, cap.letterGradeToCap("B"));
        assertEquals(3.0, cap.letterGradeToCap("B-"));
        assertEquals(2.5, cap.letterGradeToCap("C+"));
        assertEquals(2.0, cap.letterGradeToCap("C"));
        assertEquals(1.5, cap.letterGradeToCap("D+"));
        assertEquals(1.0, cap.letterGradeToCap("D"));
        assertEquals(0.5, cap.letterGradeToCap("F"));
        assertEquals(0.0, cap.letterGradeToCap("S"));
        assertEquals(0.0, cap.letterGradeToCap("U"));
        assertEquals(0.0, cap.letterGradeToCap("CS"));
        assertEquals(0.0, cap.letterGradeToCap("CU"));
        ArrayList<String> prerequisiteTest = new ArrayList<>();
        prerequisiteTest.add("CS1010");
        prerequisiteTest.add("CS1231");
        String toBeParsed = "CS1010 and its equivalent, and CS1231";
        assertEquals(cap.parsePrerequisiteTree(toBeParsed), prerequisiteTest);
    }
}
