package task;

import wrapper.MyDate;

import java.util.Date;

public class Event extends Tasks {

//    private String starttime;
//    private String endtime;
    private MyDate date;

    public Event(String description, String type, String starttime , String endtime) {
        super(description, type);
//        this.starttime = starttime;
//        this.endtime = endtime;

        date = new MyDate(starttime , endtime);
    }

    public MyDate getDate(){
        return date;
    }

//    public String getStartTime() {
//        return starttime;
//    }
//    public String getEndTime() {
//        return endtime;
//    }

//    public void setTime(String time) {
//        this.starttime = starttime;
//    }

    public String toMessage() {
        return description
            + "(at: " + date.toString()+ ")";
    }

}