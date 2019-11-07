package oof.command;

import oof.Oof;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import oof.exception.ParserException;
import oof.exception.command.CommandException;
import oof.model.task.TaskList;
import org.junit.jupiter.api.Test;

//@@author debbiextan

public class ScheduleCommandTest {

    private Oof oof = new Oof();
    private TaskList taskList = oof.getTaskList();

    /**
     * Tests behavior when no date is given.
     */
    @Test
    public void execute_dateEmpty_exceptionThrown() {
        try {
            oof.executeCommand("schedule");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS! Please enter a date!", e.getMessage());
        }
    }

    /**
     * Tests behavior of displaying schedule by date when there are no tasks for that date.
     */
    @Test
    public void execute_noTasksOnDate_exceptionThrown() {
        String date = "01-01-2018";
        try {
            oof.executeCommand("schedule " + date);
        } catch (CommandException | ParserException e) {
            String message = "There are no Tasks scheduled on " + date + ".";
            assertEquals(message, e.getMessage());
        }
    }
}
