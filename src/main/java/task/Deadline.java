package task;

import exception.DukeException;
import parser.TimeParser;

import java.util.Date;

/**
 * Represents a specified task as Deadline by extending the {@code Task} class
 * with due {@code Date}.
 */
public class Deadline extends Task {
    private Date by;

    /**
     * Constructs the {@code Deadline} object with description and due time.
     * The due time is stored as {@code Date}.
     *
     * @param description Description of the task
     * @param ddl Deadline of the task in {@code String} type.
     * @throws DukeException If string ddl has incorrect time format.
     */
    public Deadline(String description, String ddl) throws DukeException {
        super(description);
        this.by = TimeParser.parse(ddl);
        this.toString();
    }

    /**
     * Constructs a {@code Deadline} object from the separated storage string.
     *
     * @param splitStorageStrings the separated storage string.
     */
    public Deadline(String[] splitStorageStrings) {
        super(splitStorageStrings);
        this.by = TimeParser.parse(splitStorageStrings[4]);
        this.recurringType = splitStorageStrings[5];
    }

    /**
     * Overrides the {@code toString()} method in parent class {@code Task},
     * and returns information of the task to be printed by UI.
     * e.g. "[D][✓] attend the party (by: 02/05/2019 1800)"
     *
     * @return Information of the task to be printed by UI.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + TimeParser.format(by) + ")"
                + "(" + recurringType + ")";
    }

    /**
     * Returns information of the task in storage format.
     * e.g. "D | 1 | attend the party | 02/05/2019 1800"
     *
     * @return Information of the task to be stored in storage.
     */
    @Override
    public String toStorageString() {
        return "D | " + super.toStorageString() + " | " + TimeParser.format(by) + " | " + recurringType;
    }

    public Date getBy() {
        return by;
    }

    /**
     * Reschedules the by date of the deadline.
     *
     * @param by The by {@code Date} of the event.
     */
    public void reschedule(String by) {
        this.by = TimeParser.parse(by);
    }
}
