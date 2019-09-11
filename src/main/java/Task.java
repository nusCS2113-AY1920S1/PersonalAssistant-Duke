public abstract class Task {
    protected String description;
    protected boolean isDone;

    public  Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     *  Gets the unicode icon for a tick or cross
     * @return unicode icon for a tick or cross
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    /**
     * turns isDone from false to true;
     */
    public void markDone(){
        this.isDone = true;
    }
    //
}