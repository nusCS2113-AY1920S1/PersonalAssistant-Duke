package task;

import wrapper.MyDate;

import java.util.Date;
import java.util.LinkedHashMap;

public class Event extends Tasks {

    private MyDate date;

    /**
     * Constructor for class.
     * @param description
     * @param type
     * @param starttime
     * @param endtime
     */
    public Event(String description, String type, String starttime, String endtime) {
        super(description, type);

        date = new MyDate(starttime, endtime);
    }

    public void setTime(String time) {
        this.date = new MyDate(time);
    }

    public void setTime(String time1, String time2) {
        this.date = new MyDate(time1, time2);
    }

    public MyDate getDate(){
        return date;
    }



    public String toMessage() {
        return description + "(at: " + date.toString()+ ")";
    }

}