
package taskcmdtest;//@@author mononokehime14

import gazeeebo.commands.tasks.DeadlineCommand;
import gazeeebo.commands.tasks.SnoozeCommand;
import gazeeebo.storage.Storage;
import gazeeebo.storage.TriviaStorage;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Task;
import gazeeebo.triviamanager.TriviaManager;
import gazeeebo.ui.Ui;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

import gazeeebo.exception.DukeException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RescheduleCommandTest {
    @Test
    public void testExecuteSnooze() throws ParseException,IOException {
        DeadlineCommand deadlineCommand = new DeadlineCommand();
        SnoozeCommand snoozeCommand = new SnoozeCommand();
        ArrayList<Task> tasks = new ArrayList<Task>();
        Ui ui = new Ui();
        Storage storage = new Storage();
        TriviaStorage triviaStorage = new TriviaStorage();
        TriviaManager triviaManager = new TriviaManager(triviaStorage);
        Stack<ArrayList<Task>> commandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();
        ui.fullCommand = "deadline return book /by 2008-07-07 03:03:03";
        try {
            deadlineCommand.execute(tasks,ui,storage, commandStack, deletedTask,triviaManager);
        } catch (DukeException dukeException) {
            dukeException.printStackTrace();
        }
        ui.fullCommand = "reschedule 1";
        int index = 0;
        String description = tasks.get(index).description;
        ui.fullCommand = "2019-09-18 05:05:05";
        Task rescheduledDeadline = new Deadline(description, ui.fullCommand);
        tasks.remove(index);
        tasks.add(rescheduledDeadline);

        assertEquals(tasks.get(0).toString(),"D|ND|return book |by: 2019-09-18 05:05:05");

    }
}
