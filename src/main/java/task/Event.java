package task;

import exception.DukeException;
import parser.TimeParser;

import java.util.Date;

/**
 * Represents a specified task as event by extending the <code>Task</code> class
 * with start and end <code>Date</code>.
 */
public class Event extends Task {
    private String timePiece;
    private Date start;
    private Date end;

    /**
     * Constructs an Event object with description and time period in string.
     * The time period is then stored as <code>Date</code>.
     *
     * @param description Description of the task.
     * @param timePiece String containing start and end time.
     * @throws DukeException If <code>timePiece</code> string has incorrect time format.
     */
    public Event(String description, String timePiece) throws DukeException {
        super(description);
        this.timePiece = timePiece;
        String[] tokens = timePiece.split("-");
        if(tokens.length < 2) throw new DukeException("☹ OOPS!!! Cannot only show start or end time.");
        for(int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].trim();
        }
        start = TimeParser.parse(tokens[0]);
        end = TimeParser.parse(tokens[1]);
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
        return "[E]" + super.toString() + " (at: " + timePiece + ")";
    }

    /**
     * Returns information of the event in storage format.
     * e.g. "E | 1 | attend the party | 02/05/2019 1800 - 02/05/2019 2100"
     *
     * @return Information of the event to be stored in storage.
     */
    @Override
    public String toStorageString() {
        return "E | " + super.toStorageString() + " | " + timePiece;
    }
}