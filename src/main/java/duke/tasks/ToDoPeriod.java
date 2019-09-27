package duke.tasks;

public class ToDoPeriod extends Task {

    /**
     * This is the constructor of ToDo object
     * @param description the description of the todo object
     */
    public ToDoPeriod(String begin, String end) {
        super(description);
        super.type = "T";
    }

    /**
     * this function overrides the toString() function in Task to represents the full description of a ToDo object
     * @return <code>"[T]" + super.toString()</code>
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

