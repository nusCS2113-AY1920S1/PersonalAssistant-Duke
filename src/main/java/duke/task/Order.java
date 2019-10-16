package duke.task;

import duke.Duke;
import duke.parser.Convert;

import java.util.*;

/**
 * Represents a general Order to be added by {@link Duke}.
 */
public class Order {

    private HashMap<Integer, Dish> content;
    private boolean isDone;
    private Date date;

    /**
     * The constructor method for {@link Order}.
     */
    public Order() {
        this.isDone = false;
        this.date = new Date();
        this.content = new HashMap<Integer, Dish>();
    }

    /**
     * The constructor method for the {@link Order} in reservation case.
     * @param date date of serving the {@link Order}.
     */
    public Order(String date) {
        this.date = Convert.stringToDate(date);
        this.isDone = false;
        this.content = new HashMap<Integer, Dish>();
    }

    /**
     * Used to get the serving date of the {@link Order}.
     */
    public Date getDate() { return this.date;}

    /**
     * Used to alter the serving date of the {@link Order}.
     * @param date reset date of the {@link Order}.
     */
    public void setNewDate(String date) {
        this.date = Convert.stringToDate(date);
    }

    /**
     * Returns a boolean indicating whether the {@link Order} was completed.
     * @return boolean true if the order was marked as done, false otherwise
     */
    public boolean isDone() { return isDone; }

    /**
     * Returns a String representation of the status icon, indicating whether the {@link Order} was done.
     * @return a tick or a cross
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    /**
     * Used to mark the {@link Order} as finished.
     */
    public void markAsDone() {
        this.isDone = true;

        // trigger get feedback here

    }

    /**
     * Returns the content of the {@link Order}.
     * @return description of the Order
     */
    public HashMap<Integer, Dish> getOder() { return this.content; }


    public String printInFile() {
        //...
        return "";
    }

    public String toString() {
        return "[" + getStatusIcon() + "] " + this.content;
    }

    public int getTotalPrice() {
        //...
        return 0;
    }
}
