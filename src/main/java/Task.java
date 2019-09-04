public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) { //initialization
        this.description = description;
        this.isDone = false;
    }

    public String getStatus() {

        return "[" + (isDone ? "Y" : "N") + "]"; //return tick or X symbols in a bracket
    }

    //Return status icon as int, for easier reading when saving
    public String getStatusInt() {
        return isDone ? "1" : "0";
    }

    public void markAsDone() { //marks a task as done

        this.isDone = true;
    }

    public String getType() { //returns type of task, to be overwritten
        return "Task";
    }

    public String getBy() { //returns deadline or event date, to be overwritten
        return "Date";
    }

    public String getStatusIcon() {
        return "Full_description"; //returns the full task data, to be overwritten
    }

    public String getDescription() {

        return this.description;
    }

}
