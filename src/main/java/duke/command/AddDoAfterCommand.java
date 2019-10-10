package duke.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import duke.task.TaskList;
import duke.task.DukeException;
import duke.task.Ui;
import duke.task.Storage;
import duke.task.DoAfter;

/**
 * Represents the command to add a task to be completed after another task.
 */
public class AddDoAfterCommand extends Command {
    /**
     * Takes in a flag to represent if it should exit and the input given by the User.
     * @param isExit True if the program should exit after running this command, false otherwise
     * @param input Input given by the user
     */
    public AddDoAfterCommand(boolean isExit, String input) {
        super(isExit, input);
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (input.length() < 9) {
            throw new DukeException("OOPS!!! The description of a task cannot be empty.");
        }
        input = input.substring(8);
        int startIndex = input.indexOf("/after ");
        if (startIndex == -1) {
            throw new DukeException("OOPS!!! Please indicate the task or date after \"/from\"");
        }

        String after = input.substring(startIndex + 7);
        LocalDateTime afterTime = null;
        int afterTask = 0;
        try {
            afterTask = Integer.parseInt(after);
            if (afterTask > taskList.getSize()) {
                throw new DukeException("You have entered a number larger than the number of tasks.");
            }

            if (afterTask <= 0) {
                throw new DukeException("Please enter a number larger than 0");
            }

        } catch (NumberFormatException e) {
            afterTime = parseDate(after);
        }

        String task = input.substring(0, startIndex - 1);
        DoAfter toAdd;

        if (afterTime != null) {
            if (afterTime.compareTo(LocalDateTime.now()) < 1) {
                throw new DukeException("Date and time cannot be earlier than now.");
            }
        }

        if (afterTask > 0) {
            toAdd = new DoAfter(task, afterTime, taskList.getTask(afterTask - 1).description);
        } else {
            toAdd = new DoAfter(task, afterTime, null);

        }
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
            throw new DukeException("OOPS!!! Please format your date and time in this format \"20/12/2019 1859\" "
                    + "or insert a task number");
        }
    }

}
