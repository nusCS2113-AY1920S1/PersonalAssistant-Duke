package Model_Classes;

import java.util.Date;

/**
 * An object class representing tasks the are leaves.
 * Stores the description as well as the start and end time of the leave.
 */

public class Leave extends Task {
    private Date from;
    private Date to;
    private String user;


    public Leave(String description, String user, Date from, Date to) {
        super(description, from);
        this.user = user;
        this.from = from;
        this.to = to;
    }

    public Date getStartDate() {
        return this.from;
    }

    public void setStartDate(Date date) {
        this.from = date;
    }

    public Date getEndDate() {
        return this.to;
    }

    public void setEndDate(Date date) {
        this.to = date;
    }

    @Override
    public String getAssignee() {
        return this.user;
    }

    @Override
    public String toString() {
        return "[L] " + super.getDescription() + " (" + user + ")" + " (From: " + from + " To: " + to + ")";
    }
}