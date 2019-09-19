package duke.command;

import duke.task.*;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Holds the command to add a task that has a fixed duration.
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

        String task = input.substring(0, dateIndex-1);

        Duration duration = parseInputTiming( input.substring(dateIndex+5));
        if (duration == null) {
            return;
        }
        FixedDuration toAdd = new FixedDuration(task, duration);
        taskList.addToArrayList(toAdd);

        ui.output = "Got it. I've added this task: \n  " + toAdd.toString() + "\nNow you have " + taskList.getSize() + " task(s) in the list.";
        storage.saveToFile();
    }

    /**
     * Used to convert a string given to an appropriate LocalDateTime Object.
     * @param toParse String to be converted
     * @return Duration representing length of duration
     * @throws DukeException Thrown if the input given does not match the format
     */

    private Duration parseInputTiming(String toParse) throws DukeException {
        String[] toParseArray = toParse.split(" ");
        if (toParseArray.length < 2 || toParseArray.length % 2 != 0) {
            throw new DukeException("Please enter a duration in this format \"2 HRS 3 HRS 5 MINS 12 SEC\"");
        }
        Duration newDuration;
        String durationString = "P";

        int dayValue = searchArray(toParseArray, "DAYS");
        int hrValue = searchArray(toParseArray, "HRS");
        int minValue = searchArray(toParseArray, "MINS");
        int secValue = searchArray(toParseArray, "SEC");

        if (dayValue != -1) {
            durationString += toParseArray[dayValue-1] + "D";
        }

        if (hrValue != -1) {
            durationString += "T" + toParseArray[hrValue-1] + "H";
        }

        if (minValue != -1) {
            if (durationString.contains("T")) {
                durationString += toParseArray[minValue-1] + "M";
            } else {
                durationString += "T" + toParseArray[minValue-1] + "M";
            }
        }

        if (secValue!=-1) {
            if (durationString.contains("T")) {
                durationString += toParseArray[secValue-1] + "S";
            } else {
                durationString += "T" + toParseArray[secValue-1] + "S";
            }
        }

        if (durationString.equals("P")) {
            throw new DukeException("Please enter a duration in this format \"2 HRS 3 HRS 5 MINS 12 SEC\"");
        }
        newDuration = Duration.parse(durationString);

        return newDuration;
    }
    private int searchArray(String[] toFindArray, String toFindValue) {
        int i = 0;
        for (i = 0; i < toFindArray.length; ++i) {
            if (toFindArray[i].equals(toFindValue)) {
                return i;
            }
        }
        return -1;
    }
}
