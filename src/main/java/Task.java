/**
 * Abstract class representing a Task
 */
abstract class Task {
    private String task;
    private String mark;

    /**
     * Constructor of Task
     * @param task String representing the description of the Task.
     */
    protected Task (String task){
        this.task = task;
        this.mark = "[✗]";
    }

    /**
     * Setter of the mark of the Task ( done or not).
     * @param mark String ( "[✓]" or "[✗]");
     */
    protected void setMark(String mark){
        this.mark= mark;
    }

    /**
     * Set the task to done
     */
    protected void taskDone(){
        this.mark = "[✓]";
    }

    /**
     * Getter of the description of the Task.
     * @return a string representing the description of the task.
     */
    protected String getTask(){
        return this.task;
    }

    /**
     * Returns the mark of the task.
     * Allows to know if the task is done or not.
     * @return a String which allows to know if the task is done or not.
     */
    protected String getMark(){
        return this.mark;
    }

    /**
     * Getter of the tag of the task.
     * Allows to know if it is a todo, deadline or event task.
     * @return a String which allows to know if it is a todo, deadline or event task.
     */
    public abstract String getTag();
}
