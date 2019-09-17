package duke.tasks;

import duke.DateTime;

import java.util.Date;

public class Event extends Task {
    private DateTime start;
    private DateTime end;

    /**
     * Constructor for Duke.Tasks.Event object.
     * @param description name of the event.
     * @param start Date object for start DateTime.
     * @param end Date object for end DateTime.
     */
    public Event(String description, Date start, Date end) {
        super(description);
        this.start = new DateTime(start);
        this.end = new DateTime(end);
    }


    /**
     * This constructor is used for recreation of Duke.Tasks.Deadline from storage.
     * @param done  1 if task has been marked complete, 0 otherwise.
     * @param description the name or description of the event.
     * @param start Date object for start DateTime.
     * @param end Date object for end DateTime.
     */
    public Event(int done, String description, Date start, Date end) {
        super(description);
        this.isDone = (done == 1);
        this.start = new DateTime(start);
        this.end = new DateTime(end);
    }

    @Override
    public String storeString() {
        return "E | " + super.storeString() + " | " + this.getStart() + " | " + this.getEnd();
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
