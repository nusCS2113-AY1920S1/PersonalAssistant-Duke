import gazeeebo.commands.tasks.DeadlineCommand;
import gazeeebo.commands.tasks.SnoozeCommand;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

import gazeeebo.exception.DukeException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RescheduleCommandTest {
    @Test
    public void testExecuteSnooze() throws ParseException,IOException,DukeException{
        DeadlineCommand deadlineCommand = new DeadlineCommand();
        SnoozeCommand snoozeCommand = new SnoozeCommand();
        ArrayList<Task> tasks = new ArrayList<Task>();
        Ui ui = new Ui();
        Storage storage = new Storage();
        TriviaManager triviaManager = new TriviaManager();
        Stack<String> CommandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();
        ui.fullCommand = "deadline return book /by 2008-07-07 03:03:03";
        try {
            deadlineCommand.execute(tasks,ui,storage, CommandStack, deletedTask,triviaManager);
        } catch (DukeException dukeException) {
            dukeException.printStackTrace();
        }
        ui.fullCommand = "reschedule 1";
        int index =0;
        String Decription = tasks.get(index).description;
        ui.fullCommand ="2019-09-18 05:05:05";
        Task RescheduledDeadline = new Deadline(Decription, ui.fullCommand);
        tasks.remove(index);
        tasks.add(RescheduledDeadline);

        assertEquals(tasks.get(0).toString(),"D|ND|return book |by: 2019-09-18 05:05:05");

    }
}
