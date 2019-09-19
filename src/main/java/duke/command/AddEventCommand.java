package duke.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import duke.task.TaskList;
import duke.task.DukeException;
import duke.task.Ui;
import duke.task.Storage;
import duke.task.Event;
import duke.task.Task;

/**
 * Represents the command to add an event.
 */
public class AddEventCommand extends Command {
    /**
     * Takes in a flag to represent if it should exit and the input given by the User.
     * @param isExit True if the program should exit after running this command, false otherwise
     * @param input Input given by the user
     */
    public AddEventCommand(Boolean isExit, String input) {
        super(isExit, input);
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (this.input.length() < 7) {
            throw new DukeException("OOPS!!! The description of an event cannot be empty.");
        }
        input = input.substring(6);

        int dateIndex = input.indexOf("/at ");
        if (dateIndex == -1) {
            throw new DukeException("OOPS!!! Please indicate the event timing after \"/at\"");

        }
        String task = input.substring(0, dateIndex - 1);

        String at = input.substring(dateIndex + 4);

        LocalDateTime atValue = parseDate(at);
        if (atValue == null) {
            return;
        }

        int i = 0;
        int clash = 0;
        String anomalies = "";
        while (i < taskList.getSize()) {
            Task type = taskList.getTask(i);
            if (type.toString().contains(at)) {
                int index = type.toString().indexOf("at: ");
                anomalies += "There is a clash with " + type.toString().substring(14, index - 2) + " on " + at + "\n";
                clash++;
            }
            i++;
        }

        if (clash != 0) {
            ui.output = anomalies;
        } else {
            Event toAdd = new Event(task, atValue);
            taskList.addToArrayList(toAdd);
            ui.output = "Got it. I've added this task: \n  " + toAdd.toString()
                    + "\nNow you have " + taskList.getSize() + " task(s) in the list.";
            storage.saveToFile();
        }
    }

    /**
     * Converts a string given to an appropriate LocalDateTime Object.
     * @param dateToParse String to be converted
     * @return LocalDateTime object in d/M/yyyy HHmm format (2/2/2019 1830)
     * @throws DukeException Thrown if the input given does not match the format
     */
    private LocalDateTime parseDate(String dateToParse) throws DukeException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            return LocalDateTime.parse(dateToParse, formatter);
        } catch (DateTimeParseException e) {
            throw new DukeException("OOPS!!! Please format your date and time in this format \"20/12/2019 1859\".");
        }
    }
}

