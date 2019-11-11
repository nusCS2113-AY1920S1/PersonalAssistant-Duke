import chronologer.command.Command;
import chronologer.command.ListCommand;
import chronologer.exception.ChronologerException;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.ui.UiMessageHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Tests both positive/negative cases for the list that the GUI obtains.
 *
 * @author Sai Ganesh Suresh
 * @version v1.4
 */
public class ListCommandTest {

    private static ArrayList<Task> testCoreList;
    private static TaskList testCore;
    private static final String EMPTY_LIST = "You have currently no tasks in your list.";

    @BeforeAll
    public static void setup() {
        testCoreList = new ArrayList<Task>();
        testCore = new TaskList(testCoreList);
    }

    @Test
    public void testListCommand() throws ChronologerException {
        Command list = new ListCommand();
        list.execute(testCore, null, null);
        Assertions.assertEquals(UiMessageHandler.getOutputForGui(), EMPTY_LIST);
    }
}