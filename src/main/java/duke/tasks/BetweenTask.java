package duke.tasks;

/**
 * @author Suther David Samuel (A0182488N)
 * A class inheriting from duke.tasks.Task used to represent tasks that have both a description and
 * has to be done within a period
 */
public class BetweenTask extends Task {
    private String start_date;
    private String end_date;

    /**
     * Constructor for a duke.tasks.BetweenTask task, which consists of the description of the task and the period
     * associated with it.
     *
     * @param description refers to the description of the task
     * @param start_date refers to the start of the period
     * @param end_date refers to the end of the period
     */
    public BetweenTask(String description, String start_date, String end_date){
        super(description);
        this.start_date = start_date;
        this.end_date = end_date;
    }

    /**
     * Returns a String representation of the duke.tasks.BetweenTask object, displaying its type (duke.tasks.BetweenTask),
     * description and the period associated with it.
     *
     * @return a String representation of the duke.tasks.BetweenTask object
     */
    @Override
    public String toString(){
        return "[B]" + super.toString() + " (between: " + start_date + " and " + end_date +")";
    }

}
