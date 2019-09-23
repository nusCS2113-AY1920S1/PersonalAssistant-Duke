package JavaFx;

public class Timetable {
    private String time;
    private String event;
    private String priority;

    public Timetable(){
        this.time = "";
        this.event = "";
        this.priority = "low";
    }

    public Timetable(String time, String event, String priority){
        this.time = time;
        this.event = event;
        this.priority = priority;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
