//@@author e0313687

package planner.logic.command;

import org.junit.jupiter.api.Test;
import planner.main.InputTest;
import planner.logic.exceptions.planner.ModFailedJsonException;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTasksList;
import planner.logic.parser.Parser;
import planner.ui.cli.PlannerUi;
import planner.util.crawler.JsonWrapper;
import planner.util.legacy.reminder.Reminder;
import planner.util.storage.Storage;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortTest extends InputTest {
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

    @Test
    public void sortTestUserInput() {
        final String commandTest1 = "sort ccas f\n" + "bye";
        final String commandTest2 = "sort ccas r\n" + "bye";
        final String commandTest3 = "sort times monday\n" + "bye";
        final String commandTest4 = "sort times tuesday\n" + "bye";
        final String commandTest5 = "sort times wednesday\n" + "bye";
        final String commandTest6 = "sort times thursday\n" + "bye";
        final String commandTest7 = "sort times friday\n" + "bye";
        final String commandTest8 = "sort times saturday\n" + "bye";
        final String commandTest9 = "sort times sunday\n" + "bye";
        final String commandTest10 = "sort modules code\n" + "bye";
        final String commandTest11 = "sort modules grade\n" + "bye";
        final String commandTest12 = "sort modules level\n" + "bye";
        final String commandTest13 = "sort modules mc\n" + "bye";

        final String[] hold = {""};
        String sortedCcas = "_______________________________\n"
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
                "_______________________________\n"
                +
                "Thanks for using ModPlanner!\n"
                +
                "Your data will be stored in file shortly!\n"
                +
                "_______________________________\n"
                +
                "_______________________________";
        String sortedTimes = "_______________________________\n"
                +
                "Welcome to ModPlanner, your one stop solution to module planning!\n"
                +
                "Begin typing to get started!\n"
                +
                "_______________________________\n"
                +
                "_______________________________\n"
                +
                "Here are your sorted times:\n"
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
        String sortedModules = "_______________________________\n"
                +
                "Welcome to ModPlanner, your one stop solution to module planning!\n"
                +
                "Begin typing to get started!\n"
                +
                "_______________________________\n"
                +
                "_______________________________\n"
                +
                "Here are your sorted modules:\n"
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
        assertEquals(outContent, outContent);
    }

    /*@Test
    public void sortTestDummyClass() throws ModException {
        ModuleInfoDetailed mod1 = modDetailedMap.get("CS1010");
        ModuleTask add1 = new ModuleTask("CS1010", mod1);
        modTasks.getTasks().add(add1);
        assertEquals(add1.getModuleCode(), "CS1010");
        assertEquals(add1.getModuleCredit(), "4");
        assertEquals(add1.getModuleInfoDetailed().getAttributes().isSu(), true);
        SortCommand test = new SortCommand("Ccas","f");

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
