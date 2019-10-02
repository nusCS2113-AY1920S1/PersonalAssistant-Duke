package command;

import exception.DukeException;
import storage.Storage;
import task.DoWithinPeriod;
import task.TaskList;
import ui.Ui;

import java.io.IOException;

/**
 * command.Command to add period for task to be completed within.
 */

public class DoWithinPeriodCommand extends Command{

    private String[] doWithin;
    private String[] periods;

    /**
     * Create new To Do with fixed duration.
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException if description empty or format not followed
     */
    public DoWithinPeriodCommand(String input, String[] splitStr) throws DukeException {
        if (splitStr.length == 1) {
            throw new DukeException("☹ OOPS!!! The description of todo with duration cannot be empty.");
        }
        String tempD = input.substring(8);
        if (!tempD.contains(" /period ")) {
            throw new DukeException("☹ OOPS!!! Please add a period for the task to be completed within.");
        }
        if (!tempD.contains(" /til ")) {
            throw new DukeException("☹ OOPS!!! Please enter task period correctly: <date_1> /til <date_2> ");
        }

        this.doWithin = tempD.split(" /period ");
        this.periods = doWithin[1].split("/til");

    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        DoWithinPeriod duration = new DoWithinPeriod(doWithin[0], periods[0], periods[1]);
        tasks.add(duration);
        storage.saveToFile(tasks);
        ui.showString("Got it. I've added this task:\n"
                + duration.toString() + "\n"
                + "Now you have " + tasks.size() + " task(s) in the list");
    }
}