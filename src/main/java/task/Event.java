package task;

import exception.DukeException;
import parser.TimeParser;
import ui.Ui;
import utils.DateCompare;

import java.util.Date;

/**
 * Represents a specified task as event by extending the {@code Task} class
 * with start and end {@code Date}.
 */
public class Event extends Task {
    private String startString;
    private String endString;
    private Date start;
    private Date end;

    /**
     * Constructs an Event object with description and time period in string.
     * The time period is then stored as {@code Date}.
     *
     * @param description Description of the task.
     * @param startString String containing the start time.
     * @param endString   String containing the end time.
     * @throws DukeException If {@code timePiece} string has incorrect time format.
     */
    public Event(String description, String startString, String endString, TaskList taskList) throws DukeException {
        super(description);
        this.startString = startString;
        this.endString = endString;
        this.start = TimeParser.parse(startString);
        this.end = TimeParser.parse(endString);
        TaskList overlappingTasks = overlappingWithOtherEvents(taskList);
        if (overlappingTasks.getSize() > 0) {
            Ui ui = new Ui();
            ui.println("**!Warning you have overlapping events!**");
            ui.printTaskList(overlappingTasks);
            ui.println("\n");
        }

    }

    /**
     * Constructs a {@code Event} object from the separated storage string.
     *
     * @param splitStorageStrings the separated storage string.
     */
    public Event(String[] splitStorageStrings) {
        super(splitStorageStrings);
        this.start = TimeParser.parse(splitStorageStrings[4]);
        this.end = TimeParser.parse(splitStorageStrings[5]);
        this.recurringType = splitStorageStrings[6];
    }

    /**
     * Overrides the {@code toString()} method in parent class {@code Task},
     * and returns information of the event to be printed by UI.
     * e.g. "[E][✓] attend the party (at: 02/05/2019 1800 - 02/05/2019 2100)"
     *
     * @return Information of the event to be printed by UI.
     */
    @Override
    @SuppressWarnings("checkstyle:AvoidEscapedUnicodeCharacters")
    public String toString() {
        return "[E]" + super.toString() + " (at: "
                + TimeParser.format(start) + " - " + TimeParser.format(end) + ")"
                + "(" + recurringType + ")" + getTentativeString();
    }

    /**
     * Returns information of the event in storage format.
     * e.g. "E | 1 | attend the party | 02/05/2019 1800 - 02/05/2019 2100"
     *
     * @return Information of the event to be stored in storage.
     */
    @Override
    @SuppressWarnings("checkstyle:AvoidEscapedUnicodeCharacters")
    public String toStorageString() {
        return "E | " + super.toStorageString() 
                + " | " + TimeParser.format(start) + " | " + TimeParser.format(end)
                + " | " + recurringType;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    /**
     * Reschedules the start and end dates of the event.
     *
     * @param start The start {@code Date} of the event.
     * @param end   The end {@code Date} of the event.
     */
    public void reschedule(String start, String end) {
        this.start = TimeParser.parse(start);
        this.end = TimeParser.parse(end);
    }

    /**
     * Detect overlapping instances of {@code Event}.
     * @param taskList the {@code TaskList} of Duke
     * @return the overlapping events in a {@code TaskList}
     */
    public TaskList overlappingWithOtherEvents(TaskList taskList) {
        Event event = this;
        TaskList overlappingTasks = new TaskList();
        for (Task task : taskList.getTasks()) {
            if (task instanceof Event) {
                Event otherEvent = (Event) task;
                if (DateCompare.isSameDay(event.start, otherEvent.start)
                        || DateCompare.isSameDay(event.start, otherEvent.end)) {
                    if (DateCompare.isOverlapping(event.start, event.end, otherEvent.start, otherEvent.end)) {
                        overlappingTasks.addTask(otherEvent);
                    }
                }
            }
        }
        return overlappingTasks;
    }
}