package tasks;

import java.text.SimpleDateFormat;

public class Last extends Task{

    private String duration;

    public Last(){}

    public Last(String description, String duration){
        super(description);
        this.duration = duration;
    }

    public void setDuration(String duration){
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "[L]" + super.toString() + " (last: " + duration + ")";
    }

    @Override
    public String dataString() {

        return "L | " + (this.isDone ? 1 : 0) + " | " + this.description + " | " + this.duration;
    }

}
