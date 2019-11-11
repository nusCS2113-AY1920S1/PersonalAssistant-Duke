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

