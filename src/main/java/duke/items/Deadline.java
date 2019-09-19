package duke.items;

/**
 * In addition to the deadline and done status (inherited from Task),
 * the Deadline has a doByDate that is represented by a date class.
 * The save and print strings have been overridden to show more information.
 */

public class Deadline extends Task {

    private String doBy;
    private DateTime doByDate;

    /**
     * Deadline object has a "by" string as well as a Date object.
     */
    public Deadline(int index, String description, String by) {
        super(index, description, TaskType.DEADLINE); //Using the Task constructor. isDone is set to false.
        this.doBy = by;
        this.doByDate = new DateTime(doBy);
    }

    public String getDoBy() {
        return doBy;
    }

    public String getDoByDate() {
        return doByDate.returnFormattedDate();
    }

    @Override
    public String saveDetailsString() {
        return super.saveDetailsString() + "/" + doBy;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + doByDate.returnFormattedDate() + ")";
    }
}