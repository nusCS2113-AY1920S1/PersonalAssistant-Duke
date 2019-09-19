package duke.tasks;
import java.time.LocalDateTime;

/**
 * A class inheriting from duke.tasks.Task used to represent tasks that have both a description and an
 * associated location.
 */
public class Event extends Task {
    
    private String at;
    protected String end;
    protected LocalDateTime end_date;
    protected boolean date = false;
    protected String start;
    protected LocalDateTime start_date;

    @Override
    public String get_type() {
        return "E";
    }

    /**
     * Constructor for the duke.tasks.Event object, which consists of the description of a task and a
     * location that is associated with it.
     *
     * @param description the description of the task
     * @param at the location associated with the task
     */
    public Event(String description,String start, String end) {
        super(description);
        this.end = end;
        this.start = start;
    }
    public Event(String description,LocalDateTime start_d,LocalDateTime end_d, String command_start, String command_end) {
        super(description);
        end_date = end_d;
        start_date = start_d;
        date = true;
        this.end = command_end.trim();
        this.start = command_start.trim();
    }


    /**
     * Returns a String representation of the duke.tasks.Event object, displaying its type (duke.tasks.Event),
     * description and the location associated with it.
     *
     * @return a String representation of the duke.tasks.Event object
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start.trim() + " to " + end.trim() + ")";
    }
    public boolean has_date(){
        return date;
    }
    public LocalDateTime get_start_date(){
        return start_date;
    }
    public LocalDateTime get_end_date(){
        return end_date;
    }

}