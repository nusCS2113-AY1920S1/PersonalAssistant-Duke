package leduc.task;

import leduc.Date;

/**
 * Represents a deadline Task.
 */
public class DeadlinesTask extends Task {
    private String tag; // [D]
    private Date deadlines;

    /**
     * Constructor of leduc.task.DeadlinesTask. The task is not done by default.
     * @param task task list.
     * @param deadlines the deadline of the task.
     */
    public DeadlinesTask(String task, Date deadlines){
        super(task);
        this.tag ="[D]";
        this.deadlines = deadlines;
    }

    /**
     * Constructor of leduc.task.DeadlinesTask. The task could be done or not depending on the parameter given.
     * @param task task list.
     * @param mark represent if the task is done or not.
     * @param deadlines the deadline of the task.
     */
    public DeadlinesTask(String task, String mark, Date deadlines){
        super(task);
        super.setMark(mark);
        this.tag ="[D]";
        this.deadlines = deadlines;
    }

    /**
     * Getter of Tag ( [D] ).
     * @return String : [D]
     */
    public String getTag(){ return this.tag;}

    /**
     * Getter of deadline.
     * @return the deadline date of the task.
     */
    public Date getDeadlines(){ return this.deadlines;}

    /**
     * to know if whether is a deadline task of not
     * @return true
     */
    @Override
    public boolean isDeadline(){
        return true;
    }

    /**
     * visualize a deadline task
     * @return the string format to see a deadline task
     */
    public String toString(){
        return super.toString() + " by: " + getDeadlines();
    }
}

