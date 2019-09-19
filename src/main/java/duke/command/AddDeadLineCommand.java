package duke.command;

import duke.task.TaskList;
import duke.task.DukeException;
import duke.task.Ui;
import duke.task.Storage;
import duke.task.Deadline;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents the command to add a deadline.
 */
public class AddDeadLineCommand extends Command {
    /**
     * Takes in a flag to represent if it should exit and the input given by the User.
     * @param isExit True if the program should exit after running this command, false otherwise
     * @param input Input given by the user
     */
    public AddDeadLineCommand(boolean isExit, String input) {
        super(isExit, input);
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (input.length() < 10) {
            throw new DukeException("OOPS!!! The description of a deadline cannot be empty.");
        }
        input = input.substring(9);
        int dateIndex = input.indexOf("/by ");
        if (dateIndex == -1) {
            throw new DukeException("OOPS!!! Please indicate the deadline after \"/by\"");
        }

        String by = input.substring(dateIndex + 4);
        String task = input.substring(0, dateIndex - 1);
        LocalDateTime byValue = parseDate(by);
        if (byValue == null) {
            return;
        }
        Deadline toAdd = new Deadline(task, byValue);
        taskList.addToArrayList(toAdd);
        ui.output = "Got it. I've added this task: \n  " + toAdd.toString()
                + "\nNow you have " + taskList.getSize() + " task(s) in the list.";
        storage.saveToFile();
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
            throw new DukeException("OOPS!!! Please format your date and time in this format \"20/12/2019 1859\"");
        }
    }

}
