package entertainment.pro.model;

public class Deadline extends Tasks  {

    private MyDate date;

    /**
     * Constructor for a deadline class where the user watched a movie by a certain time
     *
     * @param description: which is the name of movie
     * @param type: which is the type of movie
     * @param deadline: which is the time by which the movie needs to be watched
     */
    public Deadline(String description, String type, String deadline) {
        super(description, type);
        //this.deadline = deadline;
        date = new MyDate(deadline);
    }

    /**
     * returns the deadline of the task in date format
     * @return deadline of the task in date format
     */
    public MyDate getDate() {
        return date;
    }

    /**
     * function to change the deadline of the task
     * @param time: new deadline for the task to be changed to
     */
    public void setTime(String time) {
        this.date = new MyDate(time);
    }

    /**
     * returns the description of a task
     * @return String type of the description of a task
     */
    public String toMessage() {
        return description + " (by: " + date.toString() + ")";
    }

}

