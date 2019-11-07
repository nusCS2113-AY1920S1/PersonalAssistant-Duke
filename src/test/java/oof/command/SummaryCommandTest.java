package oof.command;

import oof.Oof;
import oof.exception.ParserException;
import oof.exception.command.CommandException;
import oof.model.task.TaskList;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author debbiextan

public class SummaryCommandTest {

    private Oof oof = new Oof();
    private TaskList taskList = oof.getTaskList();
    private static final int INDEX_DATE = 0;

    /**
     * Tests behavior of displaying tomorrow's schedule where there are no tasks.
     */
    @Test
    public void execute_tomorrowSummaryWithoutTasks_scheduleShown() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String tomorrow = simpleDateFormat.format(date);

        try {
            oof.executeCommand("summary");
            TaskList scheduleTasks = new TaskList();
        } catch (CommandException | ParserException e) {
            String message = "There are no Tasks scheduled on " + tomorrow + ".";
            assertEquals(message, e.getMessage());
        }
    }
}
