/**
 * In addition to the deadline and done status (inherited from Task),
 * the Deadline has a doByDate that is represented by a date class.
 * The save and print strings have been overridden to show more information.
 */

public class Deadline extends Task {

    private String doBy;
    private Date doByDate;

    public Deadline(String description, String by, int taskIndex) {
        super(description, TaskType.DEADLINE); //Using the Task constructor. isDone is set to false.
        this.doBy = by;
        this.doByDate = new Date(doBy);
    }

    public String getDoBy() {
        return doBy;
    }

    @Override
    public String saveDetailsString() {
        return "D/" + super.saveDetailsString() + "/" + doBy;
    }

    public String getDoByDate() {
        return doByDate.returnFormattedDate();
    }

    @Override
    public String toString() {
        return "[D] " + super.toString() + " (by: " + doByDate.returnFormattedDate() + ")";
    }
}