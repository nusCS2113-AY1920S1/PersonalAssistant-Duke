package JavaFx;

public class TimetableView {
    private String to;
    private String from;
    private String task;

    public TimetableView(){
        this.to = "";
        this.from = "";
        this.task = "";
    }

    public TimetableView(String time, String event, String task){
        this.to = time;
        this.from = event;
        this.task = task;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String To) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String From) {
        this.from = from;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}