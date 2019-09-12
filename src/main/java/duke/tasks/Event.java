package duke.tasks;

import duke.DateTime;
import duke.exceptions.DukeException;

public class Event extends Task {
    private DateTime start;
    private DateTime end;

    /**
     * Constructor for Duke.Tasks.Event object.
     * @param description name of the event.
     * @param at Will be broken into two dateTime objects.
     * @throws DukeException thrown from Duke.DateTime object.
     */
    public Event(String description, String at) throws DukeException {
        super(description);
        String[] duration = at.split("_");
        this.start = new DateTime(duration[0]);
        this.end = new DateTime(duration[1]);
    }

    /**
     * This constructor is used for recreation of Duke.Tasks.Deadline from storage.
     * @param done 1 if task has been marked complete, 0 otherwise.
     * @param description the name or description of the event.
     * @param at the actual date/time/duration of the event.
     */
    public Event(int done, String description, String at) throws DukeException {
        super(description);
        this.isDone = (done == 1);
        String[] duration = at.split("_");
        this.start = new DateTime(duration[0]);
        this.end = new DateTime(duration[1]);
    }

    @Override
    public String storeString() {
        return "E | " + super.storeString() + " | " + this.getStart() + "_" + this.getEnd();
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + this.getStart() + " to " + this.getEnd() + ")";
    }

    private String getStart() {
        return this.start.toString();
    }

    private String getEnd() {
        return this.end.toString();
    }
}
