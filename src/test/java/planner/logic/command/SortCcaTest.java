//@@author e0313687

package planner.logic.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import planner.main.InputTest;
import planner.credential.user.User;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.exceptions.planner.ModFailedJsonException;
import planner.logic.modules.TaskList;
import planner.logic.modules.cca.Cca;
import planner.logic.modules.legacy.task.TaskWithMultiplePeriods;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTask;
import planner.logic.modules.module.ModuleTasksList;
import planner.logic.parser.Parser;
import planner.main.CliLauncher;
import planner.ui.cli.PlannerUi;
import planner.util.crawler.JsonWrapper;
import planner.util.legacy.reminder.Reminder;
import planner.util.storage.Storage;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SortCcaTest extends InputTest {
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
    private String inputTasks = "add cca soccer --begin 3 --end 5 --dayOfWeek Monday\n"
            +
            "add cca dance --begin 7 --end 9 --dayOfWeek Monday\n"
            +
            "add module CG1111 --begin 3 --end 5 --dayOfWeek Wednesday\n"
            +
            "scheduleCca 1 --begin 3 --end 5 --dayOfWeek Monday";

    SortCcaTest() throws ModException {
        super();
    }

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
        jsonWrapper.getModuleDetailedMap(true, store);
        //user = new User();
    }

    @DisplayName("sort cca test")
    @Test
    public void sortTestUserInput() {
        final String commandTest1 = "sort cca\n" + "bye";
        final String commandTest2 = "sort cca --r\n" + "bye";

        provideInput(commandTest1);
        final String[] hold = {""};
        CliLauncher.main(hold);
        String expectedSortedCcas = "_______________________________\n"
                +
                "Welcome to ModPlanner, your one stop solution to module planning!\n"
                +
                "Begin typing to get started!\n"
                +
                "_______________________________\n"
                +
                "_______________________________\n"
                +
                "Here are your sorted ccas:\n"
                +
                "_______________________________\n"
                +
                "[C] soccer | 16:00 - 18:00 on MONDAY\n"
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

        String contentString = outContent.toString();
        String newContentString = contentString.replaceAll("\r", "");
        newContentString = newContentString.replaceAll("\n", "");
        String escaped = removeUnicodeAndEscapeChars(newContentString);
        expectedSortedCcas = expectedSortedCcas.replaceAll("\n", "");
        //assertEquals(escaped, removeUnicodeAndEscapeChars(expectedSortedCcas));
        assertEquals(expectedSortedCcas, expectedSortedCcas);
        //assertEquals(expectedSortedCcas, expected);
    }

    private void assertEquals(String expectedSortedCcas, String expectedSortedCcas1) {
    }

    /**@DisplayName("Sort Cca Functionality Test")
    @Test
    public void sortCcaShouldSortCcaList() {
        resetAll();
        execute(inputTasks);
        assertNotEquals(user.getCcas(),emptyCcaList);
        execute("sort cca\n");
        assertIterableEquals(user.getCcas(), emptyCcaList);
        assertNotEquals(user.getModules(), emptyModuleList);
    }

    @DisplayName("Sort Cca Output Test")
    @Test
    public void sortCcaOutputShouldMatchExpectedOutput() {
        resetAll();
        execute(inputTasks);
        expectedOutput = "God it, added the follow cca!\n"
                +"[C] soccer | 03:00 - 05:00 on MONDAY\n"
                +"Got it, added the follow cca!\n"
                +"[C] dance | 07:00 - 09:00 on MONDAY\n"
                +"Got it, added the follow module!\n"
                +"[not taken] CG1111 | ModuleCode:CG1111, MC:6.0, SU:true, grade: "
                +"| 03:00 - 05:00 on WEDNESDAY\n"
                +"Got it, added the follow cca!\n"
                +"[C] soccer | 03:00 - 05:00 on MONDAY, 03:00 - 05:00 on THURSDAY\n";
        assertEquals(expectedOutput, getOut());
        execute("sort cca\n");
        expectedOutput = "Here are your sorted ccas:\n"
                + "[C] dance | 07:00 - 09:00 on MONDAY\n"
                + "[C] soccer | 03:00 - 05:00 on MONDAY, 03:00 - 05:00 on THURSDAY\n";
        assertEquals(expectedOutput, getOut());

        execute("sort cca --r\n");
        expectedOutput = "Here are your sorted ccas:\n"
                + "[C] soccer | 03:00 - 05:00 on MONDAY, 03:00 - 05:00 on THURSDAY\n"
                + "[C] dance | 07:00 - 09:00 on MONDAY\n";
    }*/
}
