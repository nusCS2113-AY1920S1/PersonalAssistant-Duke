package Task;

/**
 * This is the main superclass that contains all the attributes of the tasks
 */
public class item {

    private Boolean status;
    private String info;
    private String type;
    private String date = "";


    public item (String info, Boolean status) {
        this.status = status; //true or false
        this.info = info;
    }

    /**
     * This function changes the status of the task to true
     * @return true
     */
    public Boolean changeStatus () {
        status = true;
        return true;
    }

    /**
     * Function sets the type of the item
     *
     * @param t The string type of the item
     * @return the newly set type of the item
     */
    public String setType (String t) {
        this.type = t;
        return this.type;
    }

    /**
     * Gets the type of the item
     *
     * @return String type of the item
     */
    public String getType () {
        return type;
    }

    /**
     * Sets the date of the item as defined by the standard input
     *
     * @param date The date defined by the user
     * @return newly defined date
     */
    public String setDate (String date) {

        this.date = date;
        return this.date;
    }

    /**
     * Gets the date of the item
     *
     * @return String date of the item
     */
    public String getDate () {
        return date;
    }

    /**
     * Function returns a 1 or 0 if the status of the item is complete (true)
     *
     * @return int 1 | 0
     */
    public Integer checkStatus () {
        return status ? 1 : 0;
    }

    /**
     * Function gets the info of the item
     *
     * @return info
     */
    public String getInfo() {
        return this.info;
    }

    /**
     * Function will give a tick or cross depending if the status of the item is true or false
     *
     * @return tick or cross
     */
    public String getStatusIcon() {
        return (status ? "✓" : "✗"); //return tick or X symbols
    }

    /**
     * This function prints out the string of the status icon as a tick or cross and the info of the item
     *
     * @return String phrase of all item details
     */
    public String toString() {
        String s =  "[" + this.getStatusIcon() + "] " + this.getInfo();
        return s;
    }
}