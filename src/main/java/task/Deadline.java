package task;

import wrapper.MyDate;

public class Deadline extends Tasks {

    private MyDate date;

    /**
     * Constructor for class.
     * @param description
     * @param type
     * @param deadline
     */
    public Deadline(String description, String type, String deadline) {
        super(description, type);
        date = new MyDate(deadline);
    }

    public MyDate getDate() {
        return date;
    }

    public void setTime(String time) {
        this.date = new MyDate(time);
    }

    public String toMessage() {
        return description + "(by: " + date.toString() + ")";
    }
}

