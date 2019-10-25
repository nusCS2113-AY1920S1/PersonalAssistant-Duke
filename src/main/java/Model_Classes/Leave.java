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

    public Date checkStartDate() {
        return this.from;
    }

    public Date checkEndDate() {
        return this.to;
    }

    @Override
    public String getUser() {
        return this.user;
    }

    @Override
    public String toString() {
        return "[L] " + super.getDescription() + " (" + user + ")" + " (From: " + from + " To: " + to + ")";
    }
}