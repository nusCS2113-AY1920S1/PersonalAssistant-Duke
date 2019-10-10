package duke.command;

import duke.task.TaskList;
import duke.task.DukeException;
import duke.task.Ui;
import duke.task.Storage;
import duke.task.FixedDuration;
import java.time.Duration;

/**
 * Represents the command to add a task that has a fixed duration.
 */
public class AddFixedDurationCommand extends Command {
    /**
     * Takes in a flag to represent if it should exit and the input given by the User.
     * @param isExit True if the program should exit after running this command, false otherwise
     * @param input Input given by the user
     */
    public AddFixedDurationCommand(boolean isExit, String input) {
        super(isExit, input);
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (input.length() < 7) {
            throw new DukeException("OOPS!!! The description of a fixed duration task cannot be empty.");
        }
        input = input.substring(6);
        int dateIndex = input.indexOf("/for ");
        if (dateIndex == -1) {
            throw new DukeException("OOPS!!! Please indicate the duration after \"/for\"");
        }

        String task = input.substring(0, dateIndex - 1);

        Duration duration = parseInputTiming(input.substring(dateIndex + 5));
        if (duration == null) {
            return;
        }
        FixedDuration toAdd = new FixedDuration(task, duration);
        taskList.addToArrayList(toAdd);

        ui.output = "Got it. I've added this task: \n  " + toAdd.toString()
                + "\nNow you have " + taskList.getSize() + " task(s) in the list.";
        storage.saveToFile();
    }

    /**
     * Returns Duration object specified by user input.
     * @param toParse User inputted duration, Example: 1 DAY 3 HRS 1 MIN 5 SECS
     * @return Duration representing length of duration
     * @throws DukeException Thrown if the input given does not match the format
     */

    private Duration parseInputTiming(String toParse) throws DukeException {
        String[] toParseArray = toParse.split(" ");
        if (toParseArray.length < 2 || toParseArray.length % 2 != 0) {
            throw new DukeException("Please enter a duration in this format \"2 DAY(S) 1 HR(S) 5 MIN(S) 12 SEC(S)\"");
        }
        String durationString = "P";

        int dayValue = searchArray(toParseArray, "DAYS|DAY");
        int hrValue = searchArray(toParseArray, "HRS|HR");
        int minValue = searchArray(toParseArray, "MINS|MIN");

        if (dayValue != -1) {
            durationString += toParseArray[dayValue - 1] + "D";
        }

        if (hrValue != -1) {
            durationString += "T" + toParseArray[hrValue - 1] + "H";
        }

        if (minValue != -1) {
            if (durationString.contains("T")) {
                durationString += toParseArray[minValue - 1] + "M";
            } else {
                durationString += "T" + toParseArray[minValue - 1] + "M";
            }
        }

        int secValue = searchArray(toParseArray, "SECS|SEC");

        if (secValue != -1) {
            if (durationString.contains("T")) {
                durationString += toParseArray[secValue - 1] + "S";
            } else {
                durationString += "T" + toParseArray[secValue - 1] + "S";
            }
        }

        if (durationString.equals("P")) {
            throw new DukeException("Please enter a duration in this format \"2 DAY(S) 1 HR(S) 5 MIN(S) 12 SEC(S)\"");
        }
        Duration newDuration = Duration.parse(durationString);

        return newDuration;
    }

    /**
     * Returns the index containing a specified value in an array.
     * @param toFindArray Array to search value from.
     * @param toFindValue Value to search for in array.
     * @return Index of value in array, else -1.
     * @throws DukeException thrown when element before search value is not an integer.
     */
    private int searchArray(String[] toFindArray, String toFindValue) throws DukeException {
        String[] splitArray = toFindValue.split("\\|");
        int i = 0;
        for (i = 0; i < toFindArray.length; ++i) {
            if (toFindArray[i].equals(splitArray[0]) || toFindArray[i].equals(splitArray[1])) {
                try {
                    Integer.parseInt(toFindArray[i - 1]);
                } catch (Exception e) {
                    throw new DukeException("Please enter a duration in this format "
                            + "\"2 DAY(S) 1 HR(S) 5 MIN(S) 12 SEC(S)\"");
                }
                return i;
            }
        }
        return -1;
    }
}
