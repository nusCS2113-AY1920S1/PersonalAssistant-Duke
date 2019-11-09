package duke.data;

public interface ITodo {
    String status = null;
    String startTime = null;
    String endTime = null;
    String date = null;
    String location = null;
    String className = null;

    /**
     * Get status of ToDo item.
     *
     * @return Status
     */
    String getStatus();

    /**
     * Get Start Time of ToDo item.
     *
     * @return Start Time
     */
    String getStartTime();

    /**
     * Get End Status of ToDo item.
     *
     * @return End Time
     */
    String getEndTime();

    /**
     * Get Date of ToDo item.
     *
     * @return Date
     */
    String getDate();

    /**
     * Get location of ToDo item.
     *
     * @return location
     */
    String getLocation();

    /**
     * Get Name of ToDo item.
     *
     * @return Name
     */
    String getClassName();


}
