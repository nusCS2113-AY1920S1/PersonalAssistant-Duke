package task;

import wrapper.MyDate;

public class Deadline extends Tasks {

    //private String deadline;
    private MyDate date;

    /**
     * Constructor for class.
     * @param description
     * @param type
     * @param deadline
     */
    public Deadline(String description, String type, String deadline) {
        super(description, type);
        //this.deadline = deadline;
        date = new MyDate(deadline);
    }

    //public String getDeadline() {
    // return deadline;
    // }

    public MyDate getDate(){
        return date;
    }

    //public void setDeadline(String deadline) {
    // this.deadline = deadline;
    // }

    public String toMessage() {
        return description + "(by: " + date.toString() + ")";
    }

}

