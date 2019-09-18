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
import static org.junit.jupiter.api.Assertions.fail;

public class RecurringTaskTest {
    private static DukeContext ctx;
    private static ByteArrayOutputStream testOut = new ByteArrayOutputStream();
    private static PrintStream testPrint = new PrintStream(testOut);
    private static String successStr;

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
        successStr =    "    ________________________________________________________________________________"
                + System.lineSeparator() + "    Got it, I've added these 9 tasks:"
                + System.lineSeparator() + "      [D][\u2718] submission (by: Wed, 18 Sep 2019 11:59 PM)"
                + System.lineSeparator() + "      [D][\u2718] submission (by: Wed, 26 Sep 2019 11:59 PM)"
                + System.lineSeparator() + "      [D][\u2718] submission (by: Wed, 3 Oct 2019 11:59 PM)"
                + System.lineSeparator() + "      [D][\u2718] submission (by: Wed, 10 Oct 2019 11:59 PM)"
                + System.lineSeparator() + "      [D][\u2718] submission (by: Wed, 17 Oct 2019 11:59 PM)"
                + System.lineSeparator() + "      [D][\u2718] submission (by: Wed, 24 Oct 2019 11:59 PM)"
                + System.lineSeparator() + "      [D][\u2718] submission (by: Wed, 31 Oct 2019 11:59 PM)"
                + System.lineSeparator() + "      [D][\u2718] submission (by: Wed, 7 Nov 2019 11:59 PM)"
                + System.lineSeparator() + "      [D][\u2718] submission (by: Wed, 13 Nov 2019 11:59 PM)"
                + System.lineSeparator() + "    Now you have 9 tasks in the list." + System.lineSeparator()
                + "    ________________________________________________________________________________";
    }

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
            uut.parse("deadline submission /by 18/09/2019 2359 /repeats weekly " +
                    "/until 23/11/2019 1300");
            uut.execute(ctx);
        } catch (DukeException excp) {
            fail("Exception thrown on valid recurring task!");
        }
        assertEquals(successStr, testOut.toString());
    }

    @AfterAll
    public static void clearCtx() {
        try {
            ctx.storage.writeTaskFile("");
        } catch (DukeFatalException excp) {
            fail("Something happened to data file while cleaning up after testing!");
        }
    }
}
