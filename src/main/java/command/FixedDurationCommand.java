package command;

import exception.DukeException;
import storage.Storage;
import task.FixedDuration;
import task.TaskList;
import ui.Ui;

import java.io.IOException;

/**
 * command.Command to add fixed duration to task
 */

public class FixedDurationCommand extends Command {
    String[] splitD;

    /**
     * Create new To Do with fixed duration.
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException if description empty or format not followed
     */
    public FixedDurationCommand(String input, String[] splitStr) throws DukeException {
        if (splitStr.length == 1) {
            throw new DukeException("☹ OOPS!!! The description of todo with duration cannot be empty.");
        }
        String tempD = input.substring(9);
        if (!tempD.contains(" /needs ")) {
            throw new DukeException("☹ OOPS!!! Please add a duration for the task.");
        }
        this.splitD = tempD.split(" /needs ");
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        FixedDuration duration = new FixedDuration(splitD[0], splitD[1]);
        tasks.add(duration);
        storage.saveToFile(tasks);
        ui.showString("Got it. I've added this task:\n"
                + duration.toString() + "\n"
                + "Now you have " + tasks.size() + " task(s) in the list");
    }
}