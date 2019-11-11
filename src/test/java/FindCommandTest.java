import chronologer.command.Command;
import chronologer.command.FindCommand;
import chronologer.command.ListCommand;
import chronologer.exception.ChronologerException;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.task.Todo;
import chronologer.ui.UiMessageHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Tests both positive/negative cases for the tasks with a keyword that the GUI obtains.
 *
 * @author Sai Ganesh Suresh
 * @version v1.4
 */
public class FindCommandTest {

    private static ArrayList<Task> testCoreList;
    private static TaskList testCore;
    private static final String EMPTY_LIST = "There are no matching tasks in your list.";
    private static final String PRESENT_LIST = "Here are the matching task(s) in your list:\n1.";

    private static Todo testFindableTask;

    @BeforeAll
    public static void setup() {
        testCoreList = new ArrayList<Task>();
        testCore = new TaskList(testCoreList);
        testFindableTask = new Todo("testVersion");
    }

    @Test
    public void testFindCommandNegative() throws ChronologerException {
        Command find = new FindCommand("test");
        find.execute(testCore, null, null);
        Assertions.assertEquals(UiMessageHandler.getOutputForGui(), EMPTY_LIST);
    }

    @Test
    public void testFindCommand() throws ChronologerException {
        testCore.add(testFindableTask);
        Command find = new FindCommand("test");
        find.execute(testCore, null, null);
        Assertions.assertEquals(UiMessageHandler.getOutputForGui(),
                                PRESENT_LIST + testFindableTask.toString() + "\n");
    }
}