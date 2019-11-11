import chronologer.command.Command;
import chronologer.command.WeekCommand;
import chronologer.exception.ChronologerException;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.ui.UiMessageHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Tests week command functionality.
 *
 * @author Sai Ganesh Suresh
 * @version v1.4
 */
class WeekTest {

    private static ArrayList<Task> testCoreList;
    private static TaskList testCore;
    private static String outputMessage;
    private static final int CURRENT_WEEK_INDICATOR = -1;

    @BeforeAll
    static void setup() {
        testCoreList = new ArrayList<Task>();
        testCore = new TaskList(testCoreList);
    }

    @Test
    @Order(1)
    public void testValidWeek() {
        outputMessage = testCore.updateWeek(CURRENT_WEEK_INDICATOR);
        Assertions.assertEquals("You are viewing the same week!", outputMessage);
    }

    @Test
    @Order(2)
    public void testWeekCommand() throws ChronologerException {
        Command week = new WeekCommand(CURRENT_WEEK_INDICATOR);
        week.execute(testCore, null, null);
        Assertions.assertEquals("You are viewing the same week!", UiMessageHandler.getOutputForGui());
    }

}