package Task;

/**
 * Creates a class for the Deadline task
 */
public class Deadline extends item{

    protected String by;

    /**
     * Constructor to create the deadline class
     *
     * @param info This parameter is the info of the item created
     * @param status The status of the item created, either true or false
     * @param by The date of the deadline
     */
    public Deadline(String info, Boolean status, String by) {
        super(info, status);
        super.setType("D");
        super.setDate(by);
        this.by = by;
    }


    /**
     * Gets all the info of the deadline
     *
     * @return A string phrase containing all the details of the deadline object
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
