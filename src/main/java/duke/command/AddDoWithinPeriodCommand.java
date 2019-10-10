package duke.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import duke.task.TaskList;
import duke.task.DukeException;
import duke.task.Ui;
import duke.task.Storage;
import duke.task.DoWithinPeriod;

/**
 * Represents the command to add a task to be done within a time period.
 */
public class AddDoWithinPeriodCommand extends Command {
    /**
     * Takes in a flag to represent if it should exit and the input given by the User.
     * @param isExit True if the program should exit after running this command, false otherwise
     * @param input Input given by the user
     */
    public AddDoWithinPeriodCommand(boolean isExit, String input) {
        super(isExit, input);
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (input.length() < 10) {
            throw new DukeException("OOPS!!! The description of a task cannot be empty.");
        }
        input = input.substring(9);
        int startIndex = input.indexOf("/from ");
        if (startIndex == -1) {
            throw new DukeException("OOPS!!! Please indicate the start time after \"/from\"");
        }

        int endIndex = input.indexOf("/to ");
        if (endIndex == -1) {
            throw new DukeException("OOPS!!! Please indicate the end time after \"/to\"");
        }

        String from = input.substring(startIndex + 6, endIndex - 1);
        String to = input.substring(endIndex + 4);

        LocalDateTime startValue = parseDate(from);
        if (startValue == null) {
            return;
        }
        LocalDateTime endValue = parseDate(to);
        if (endValue == null) {
            return;
        }

        if (startValue.compareTo(endValue) > 1) {
            throw new DukeException("Start time cannot be later than end time.");
        }

        String task = input.substring(0, startIndex - 1);

        DoWithinPeriod toAdd = new DoWithinPeriod(task, startValue, endValue);
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
