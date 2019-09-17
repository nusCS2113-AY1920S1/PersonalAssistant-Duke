package task;

import exception.DukeException;
import parser.TimeParser;

import java.util.Date;

/**
 * Represents a specified task as event by extending the <code>Task</code> class
 * with start and end <code>Date</code>.
 */
public class Event extends Task {
    private String startString;
    private String endString;
    private Date start;
    private Date end;

    /**
     * Constructs an Event object with description and time period in string.
     * The time period is then stored as <code>Date</code>.
     *
     * @param description Description of the task.
     * @param startString String containing the start time.
     * @param endString String containing the end time.
     * @throws DukeException If <code>timePiece</code> string has incorrect time format.
     */
    public Event(String description, String startString, String endString) throws DukeException {
        super(description);
        this.startString = startString;
        this.endString = endString;
        this.start = TimeParser.parse(startString);
        this.end = TimeParser.parse(endString);
    }

    /**
     * Overrides the <code>toString()</code> method in parent class <code>Task</code>,
     * and returns information of the event to be printed by UI.
     * e.g. "[E][✓] attend the party (at: 02/05/2019 1800 - 02/05/2019 2100)"
     *
     * @return Information of the event to be printed by UI.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + startString + " - " + endString + ")";
    }

    /**
     * Returns information of the event in storage format.
     * e.g. "E | 1 | attend the party | 02/05/2019 1800 - 02/05/2019 2100"
     *
     * @return Information of the event to be stored in storage.
     */
    @Override
    public String toStorageString() {
        return "E | " + super.toStorageString() + " | " + startString + " | " + endString;
    }
}