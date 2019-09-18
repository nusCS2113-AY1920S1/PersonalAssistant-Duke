package command;

import exception.DukeException;
import storage.Storage;
import task.FixedDuration;
import task.TaskList;
import ui.Ui;

import java.io.IOException;

public class FixedDurationCommand extends Command {
    String[] splitD;

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
        ui.showString("Got it. I've added this task:\n" +
                duration.toString() + "\n" +
                "Now you have " + tasks.size() + " task(s) in the list");
    }
}