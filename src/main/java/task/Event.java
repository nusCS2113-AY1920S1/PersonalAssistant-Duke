package task;

import wrapper.MyDate;

public class Event extends Tasks {

    private MyDate date;

    public Event(String description, String type, String starttime , String endtime) {
        super(description, type);
        date = new MyDate(starttime , endtime);
    }

    public MyDate getDate(){
        return date;
    }

    public String toMessage() {
        return description
            + "(at: " + date.toString()+ ")";
    }

}