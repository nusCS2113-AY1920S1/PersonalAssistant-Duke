package command;

import dukeException.DukeException;
import storage.Storage;
import task.Event;
import task.TaskList;
import ui.Ui;

import java.io.IOException;

/**
 * command.Command to create and save events
 */
public class EventCommand extends Command{
    String[] splitD;
    public EventCommand(String input, String[] splitStr) throws DukeException {
        if (splitStr.length == 1) throw new DukeException("☹ OOPS!!! The description of a event cannot be empty.");
        String tempD = input.substring(6);
        if (!tempD.contains(" /at ")) throw new DukeException("☹ OOPS!!! Please add a event for the task.");
        this.splitD = tempD.split(" /at ");
        if (!isValidDateTime(splitD[1])) throw  new DukeException("Please enter correct date time format: dd/mm/yyyy hhmm");
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        Event event = new Event(splitD[0], splitD[1]);
        tasks.add(event);
        storage.saveToFile(tasks);
        ui.showString("Got it. I've added this task:\n" +
                event.toString() + "\n" +
                "Now you have " + tasks.size() + " task(s) in the list.");
    }
}
