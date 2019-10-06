package command;

import exception.DukeException;
import storage.Storage;
import task.Event;
import task.TaskList;
import ui.Ui;

import java.io.IOException;

/**
 * command.Command to create and save events
 */
public class EventCommand extends Command {
    String[] splitE;
    String[] startEnd;
    boolean canAdd = true;

    /**
     * Create new Event object.
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException if description empty or format not followed
     */
    public EventCommand(String input, String[] splitStr) throws DukeException {
        if (splitStr.length == 1) {
            throw new DukeException("☹ OOPS!!! The description of a event cannot be empty.");
        }
        String tempD = input.substring(6);
        if (!tempD.contains(" /at ")) {
            throw new DukeException("☹ OOPS!!! Please add a event for the task.");
        }
        this.splitE = tempD.split(" /at ");
        if (splitE.length == 1) {
            throw new DukeException("☹ OOPS!!! Please add start and end times for the task.");
        }
        this.startEnd = splitE[1].split(" to ");
        if (startEnd.length == 1) {
            throw new DukeException("☹ OOPS!!! Please add start and end times for the task.");
        }
        if (!isValidDateTime(startEnd[0])) {
            throw  new DukeException("Please enter correct date time format: dd/mm/yyyy hhmm to dd/mm/yyyy");
        }
        if (!isValidDateTime(startEnd[1])) {
            throw  new DukeException("Please enter correct date time format: dd/mm/yyyy hhmm to dd/mm/yyyy");
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        Event event = new Event(splitE[0], startEnd[0], startEnd[1]);
        if (canAdd) {
            tasks.add(event);
            storage.saveToFile(tasks);
            ui.showString("Got it. I've added this task:\n"
                    + event.toString() + "\n"
                    + "Now you have " + tasks.size() + " task(s) in the list.");
        }
    }
}
