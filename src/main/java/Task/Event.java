package Task;

public class Event extends item{

    protected String by;

    /**
     * This method is the constructor used to create the todo class
     * @param info This is the information about the task being added
     * @param status This determines if whether the item added is completed or uncompleted
     */
    public Event(String info, Boolean status, String by) {
        super(info, status);
        super.setType("E");
        super.setDate(by);
        this.by = by;
    }

    /**
     * This function gets the type, information, and date of the task
     * @return String phrase with the type, info and date
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + by + ")";
    }
}