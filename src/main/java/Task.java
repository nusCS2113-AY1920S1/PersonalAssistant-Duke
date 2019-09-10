
public abstract class Task {
    protected String description;
    protected boolean isDone;


    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    abstract String txtFormat();

    abstract String writeTxt();

    public void markAsDone()
    {
        isDone = true;
    }

    public String printStatus()
    {
        return "[" + this.getStatusIcon() + "] " + description;
    }

    public String getDescription(){
        return description;
    }


}