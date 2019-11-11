package duke.models;

import duke.data.ITodo;

import java.util.Date;

public class ToDo implements ITodo {

    private String status;
    private String startTime;
    private String endTime;
    private String date;
    private String location;
    private String className;


    /**
     * Constructor.
     *
     * @param start the specified start time
     * @param end   the specified end time
     * @param loc   the specified location of the event
     * @param name  the specified name of the event
     */
    public ToDo(String start, String end, String loc, String name, String strDate) {
        startTime = start;
        endTime = end;
        date = strDate;
        location = loc;
        className = name;
        status = "Upcoming";
    }

    /**
     * Method will give the status of the calender.
     *
     * @return status of the item in the calender
     */
    public String getStatus() {
        return status;
    }

    /**
     * Get Start Time of ToDo item.
     *
     * @return Start Time
     */
    @Override
    public String getStartTime() {
        return startTime;
    }

    /**
     * Get End Status of ToDo item.
     *
     * @return End Time
     */
    @Override
    public String getEndTime() {
        return endTime;
    }

    /**
     * Get Date of ToDo item.
     *
     * @return Date
     */
    @Override
    public String getDate() {
        return date;
    }

    /**
     * Get location of ToDo item.
     *
     * @return location
     */
    @Override
    public String getLocation() {
        return location;
    }

    /**
     * Get Name of ToDo item.
     *
     * @return Name
     */
    @Override
    public String getClassName() {
        return className;
    }
}
