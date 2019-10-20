package duke.task;


import java.util.Date;

/**
 * This is the main superclass that contains all the attributes of the tasks.
 */
public class Item {

    /**
     * status: whether Item is complete or not.
     */
    private Boolean status;
    /**
     * info: the description of the task.
     */
    private String info;
    /**
     * type: the type of the Item created.
     */
    private String type;

    /**
     * Length of time of the item.
     */
    private String duration;

    /**
     * Constructor method for the Event class.
     *
     * @param desc   This is the information about the task being added
     * @param isDone This determines if whether the Item
     *               added is completed or uncompleted
     */
    public Item(final String desc, final Boolean isDone) {
        this.status = isDone;
        this.info = desc;
    }

    /**
     * This function changes the status of the task to true.
     *
     * @return true
     */
    public Boolean changeStatus() {
        status = true;
        return true;
    }

    /**
     * Function sets the type of the Item.
     *
     * @param t The string type of the Item
     * @return the newly set type of the Item
     */
    public String setType(final String t) {
        this.type = t;
        return this.type;
    }

    /**
     * Gets the type of the Item.
     *
     * @return String type of the Item
     */
    public String getType() {
        return type;
    }


    /**
     * Gets the date of the Item.
     *
     * @return String date of the Item
     */
    public String getDate() {
        return "";
    }

    /**
     * Designed for extension.
     *
     * @return Null if method is not inherited
     */
    public Date getRawDate() {
        return null;
    }

    /**
     * Function returns a 1 or 0 if the status of the Item is complete (true).
     *
     * @return int 1 | 0
     */
    public Integer checkStatus() {
        return status ? 1 : 0;
    }

    /**
     * Method checks whether Item is complete.
     *
     * @return if task is complete
     */
    public Boolean isDone() {
        return status;
    }

    /**
     * Function gets the info of the Item.
     *
     * @return info
     */
    public String getInfo() {
        return this.info;
    }

    /**
     * Function will give a tick or cross
     * depending if the status of the Item is true or false.
     *
     * @return tick or cross
     */
    public String getStatusIcon() {
        return (status ? "✓" : "✗"); //return tick or X symbols
    }

    /**
     * This function prints out the string of the
     * status icon as a tick or cross and the info of the Item.
     *
     * @return String phrase of all Item details
     */
    public String toString() {
        String s = "[" + this.getStatusIcon() + "] " + this.getInfo();
        return s;
    }

    /**
     * Set duration.
     *
     * @param length time of activity
     */
    public void setDuration(final String length) {
        duration = length;
    }

    /**
     * Get duration for todo task.
     *
     * @return string of duration
     */
    public String getDuration() {
        return duration;
    }
}
