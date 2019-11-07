package reminders;

import common.DukeException;
import common.LoggerController;
import logic.command.CommandOutput;
import logic.command.ReminderCommand;
import logic.parser.ArgumentTokenizer;
import logic.parser.ReminderCommandParser;
import model.Model;
import model.ModelController;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RemindersTest {
    @Test
    public void parserTest() {
        ReminderCommand rc;
        try {
            rc = ReminderCommandParser.parseReminder("rubbish");
        } catch (DukeException e) {
            assertEquals("usage: reminder [task index] [time before]\n" +
                    "*Only for tasks with dates\n" +
                    "Time before: e.g. 5m, 10h, 3d", e.getMessage());
        }

        try {
            rc = ReminderCommandParser.parseReminder("-10 -10m");
        } catch (DukeException e) {
            assertEquals("You can't set reminders after the task!", e.getMessage());
        }

        try {
            rc = ReminderCommandParser.parseReminder("10 10s");
        } catch (DukeException e) {
            assertEquals("Specify m (minutes), h (hours) or d (days)", e.getMessage());
        }
    }

    @Test
    public void commandTest() throws DukeException {
        Model model = new ModelController();
        for (int i = model.getTaskListSize() - 1; i >= 0; i--)  {
            model.deleteTask(i);
        }
        model.addTask("test");

        //execute command on newly created task
        ReminderCommand rc = ReminderCommandParser.parseReminder("" + model.getTaskListSize() + " 10h");
        CommandOutput out = rc.execute(model);
        assertEquals("Please set a time for the task first", out.getOutputToUser());

        rc = ReminderCommandParser.parseReminder("99999 10h");
        out = rc.execute(model);
        assertEquals("Please provide a valid task number", out.getOutputToUser());

        rc = ReminderCommandParser.parseReminder("0 10h");
        out = rc.execute(model);
        assertEquals("Please provide a valid task number", out.getOutputToUser());

        rc = ReminderCommandParser.parseReminder("-1 10h");
        out = rc.execute(model);
        assertEquals("Please provide a valid task number", out.getOutputToUser());
    }
}
