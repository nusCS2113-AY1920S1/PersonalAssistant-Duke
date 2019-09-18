import duke.DukeContext;
import duke.command.NewRecurringTaskCommand;
import duke.command.Ui;
import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.task.Storage;
import duke.task.TaskList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class RecurringTaskTest {
    private static DukeContext ctx;
    private static ByteArrayOutputStream testOut = new ByteArrayOutputStream(); //stores printed output
    private static PrintStream testPrint = new PrintStream(testOut); //System.out replacement, prints to testOut
    private static String successStr;

    /**
     * Create data directory if necessary and use a test task file to create test DukeContext, with output directed to
     * testOut.
     */
    @BeforeAll
    public static void setupCtx() {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            if (!dataDir.mkdir()) {
                fail("Could not create data directory!");
            }
        }
        Ui ui = new Ui(System.in, testPrint);
        try {
            ctx = new DukeContext(new Storage("data" + File.separator + "test.tsv"), ui);
            ctx.storage.writeTaskFile("");
        } catch (DukeFatalException excp) {
            fail("Could not setup storage for testing!");
        }
        successStr = "    ________________________________________________________________________________"
                + System.lineSeparator() + "    Got it, I've added these 9 tasks:"
                + System.lineSeparator() + "      [D][N] submission (by: Wed, 18 Sep 2019 11:59 PM)"
                + System.lineSeparator() + "      [D][N] submission (by: Wed, 25 Sep 2019 11:59 PM)"
                + System.lineSeparator() + "      [D][N] submission (by: Wed, 2 Oct 2019 11:59 PM)"
                + System.lineSeparator() + "      [D][N] submission (by: Wed, 9 Oct 2019 11:59 PM)"
                + System.lineSeparator() + "      [D][N] submission (by: Wed, 16 Oct 2019 11:59 PM)"
                + System.lineSeparator() + "      [D][N] submission (by: Wed, 23 Oct 2019 11:59 PM)"
                + System.lineSeparator() + "      [D][N] submission (by: Wed, 30 Oct 2019 11:59 PM)"
                + System.lineSeparator() + "      [D][N] submission (by: Wed, 6 Nov 2019 11:59 PM)"
                + System.lineSeparator() + "      [D][N] submission (by: Wed, 13 Nov 2019 11:59 PM)"
                + System.lineSeparator() + "    Now you have 9 tasks in the list." + System.lineSeparator()
                + "    ________________________________________________________________________________"
                + System.lineSeparator() + System.lineSeparator();
        //NOTE: something might not be right here, dates generated the other time were off by 1 day
    }

    /**
     * Reset taskList and testOut, and flush the testPrint stream after each test is done with them.
     */
    @AfterEach
    public void clearTaskList() {
        ctx.taskList = new TaskList();
        testPrint.flush();
        testOut.reset();
    }

    @Test
    public void addRecurringTasks_countNumber_successMessageReturned() {
        NewRecurringTaskCommand uut = new NewRecurringTaskCommand();
        try {
            uut.parse("deadline submission /by 18/09/2019 2359 /repeats weekly /count 9");
            uut.execute(ctx);
        } catch (DukeException excp) {
            fail("Exception thrown on valid recurring task!");
        }
        assertEquals(successStr, testOut.toString());
    }

    @Test
    public void addRecurringTasks_untilDate_successMessageReturned() {
        NewRecurringTaskCommand uut = new NewRecurringTaskCommand();

        try {
            uut.parse("deadline submission /by 18/09/2019 2359 /repeats weekly "
                    + "/until 23/11/2019 1300");
            uut.execute(ctx);
        } catch (DukeException excp) {
            fail("Exception thrown on valid recurring task!");
        }
        assertEquals(successStr, testOut.toString());
    }

    //TODO create unit tests for abstract Command classes

    @Test
    public void addRecurringTasks_ambiguousOrMissingCount_exceptionThrown() {
        NewRecurringTaskCommand uut = new NewRecurringTaskCommand();
        try {
            uut.parse("deadline submission /by 18/09/2019 2359 /repeats weekly");
            assertThrows(DukeException.class, () -> {
                uut.execute(ctx);
            });
            uut.parse("deadline submission /by 18/09/2019 2359 /repeats weekly "
                    + "/until 23/11/2019 1300 /count 100");
            assertThrows(DukeException.class, () -> {
                uut.execute(ctx);
            });
            uut.parse("deadline submission /by 18/09/2019 2359 /repeats weekly "
                    + "/until 231119 1300");
            assertThrows(DukeException.class, () -> {
                uut.execute(ctx);
            });
            uut.parse("deadline submission /by 18/09/2019 2359 /repeats weekly "
                    + "/count -100");
            assertThrows(DukeException.class, () -> {
                uut.execute(ctx);
            });
            uut.parse("deadline submission /by 18/09/2019 2359 /repeats weekly "
                    + "/until 23/11/2019 1300 /until 23/11/2019 1500");
            assertThrows(DukeException.class, () -> {
                uut.execute(ctx);
            });
            uut.parse("deadline submission /by 18/09/2019 2359 /repeats weekly "
                    + "/count 100 /count 10");
            assertThrows(DukeException.class, () -> {
                uut.execute(ctx);
            });
        } catch (DukeException excp) {
            fail("Parser failed to recognise valid command with '/repeats'!");
        } catch (AssertionError excp) {
            fail("Command failed to reject ambiguous count!");
        }
    }

    /**
     * Deletes testing data after test is completed.
     */
    @AfterAll
    public static void clearCtx() {
        File testData = new File("data" + File.separator + "test.tsv");
        if (!testData.delete()) {
            fail("Unable to delete test data file!");
        }
    }
}
