package duke.task;

import duke.core.DateTimeParser;
import duke.core.DukeException;

/**
 * Represents a task to be done in a certain period of time. It is
 * extended from the Task class.
 */
public class PeriodTask extends Task {

    private String startTime;
    private String endTime;
    private String startTimeEnglish;
    private String endTimeEnglish;

    /**
     * Constructs a PeriodTask object. Start time and end time are parsed and
     * stored in dateTime field if input is of "dd/MM/yyyy HHmm"
     * format.
     *
     * @param description A string that describes the specific
     *                    description of task.
     * @param startTime   A string that specifies the start dateof the
     *                    task.
     * @param startTime   A string that specifies the end date of the
     *                    task.
     */
    public PeriodTask(String description, String startTime, String endTime) throws DukeException {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
        this.startTimeEnglish = DateTimeParser.convertToEnglishDateTime(startTime);
        this.endTimeEnglish = DateTimeParser.convertToEnglishDateTime(endTime);
    }

    /**
     * Returns a string pattern to the user output
     *
     * @return A string which displays the type,
     * description and deadline of the task.
     */
    @Override
    public String toString() {
        return "[P]" + super.printStatus()
                + " (start: " + startTimeEnglish
                + " end: " + endTimeEnglish + ")";
    }

    /**
     * Returns a string with the following format to be stored in a local file
     *
     * @return A string in a specific format to be stored in a local file.
     */
    public String writeTxt() {
        return "P | " + (this.isDone ? "1" : "0") + " | " + description + " | " + startTime + " | " + endTime;
    }

}