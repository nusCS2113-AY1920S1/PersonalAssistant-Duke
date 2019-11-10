package entertainment.pro.model;

public class Deadline extends Tasks  {

    private MyDate date;

    /**
     * Constructor for class.
     *
     * @param description which is the description of the duke.task.
     * @param type        which is the type of the duke.task.
     * @param deadline    which is the time by which teh duke.task needs to completed.
     */
    public Deadline(String description, String type, String deadline) {
        super(description, type);
        //this.deadline = deadline;
        date = new MyDate(deadline);
    }

    public MyDate getDate() {
        return date;
    }

    public void setTime(String time) {
        this.date = new MyDate(time);
    }

    public String toMessage() {
        return description + " (by: " + date.toString() + ")";
    }

}
