public class Event extends Tasks {

    private String time;

    public Event(String description, String type, String time) {
        super(description, type);
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String toMessage() {
        return description
                + "(at: " + time + ")";
    }

}

