package JavaFx;

public class TodoView {
    private String task;
    private String done;

    /**
     * This creates TodoView object.
     */
    public TodoView(String task, String done){
        this.task = task;
        this.done = done;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }
}
