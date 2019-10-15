package leduc.task;

import leduc.Date;
import leduc.exception.PostponeDeadlineException;


/**
 * Represents a deadline Task.
 */
public class DeadlinesTask extends Task {
    private String tag; // [D]
    private Date deadlines;

    /**
     * Constructor of leduc.task.DeadlinesTask. The task is not done by default.
     * And the priority is 5 by default.
     * @param task String representing the description of the Task.
     * @param deadlines the deadline of the task.
     */
    public DeadlinesTask(String task, Date deadlines){
        super(task);
        this.tag ="[D]";
        this.deadlines = deadlines;
    }

    /**
     * Constructor of leduc.task.DeadlinesTask. The task is not done by default.
     * @param task  String representing the description of the Task.
     * @param deadlines the deadline of the task.
     * @param priority the priority of the deadline task.
     */
    public DeadlinesTask(String task, Date deadlines, int priority){
        super(task,priority);
        this.deadlines = deadlines;
    }

    /**
     * Constructor of leduc.task.DeadlinesTask. The task could be done or not depending on the parameter given.
     * And the priority is 5 by default.
     * @param task  String representing the description of the Task.
     * @param mark represent if the task is done or not.
     * @param deadlines the deadline of the task.
     */
    public DeadlinesTask(String task, String mark, Date deadlines){
        super(task,mark);
        this.tag ="[D]";
        this.deadlines = deadlines;
    }


    /**
     * Constructor of leduc.task.DeadlinesTask. The task could be done or not depending on the parameter given.
     * @param task  String representing the description of the Task.
     * @param mark represent if the task is done or not.
     * @param deadlines the deadline of the task.
     * @param priority the priority of the deadline task.
     */
    public DeadlinesTask(String task, String mark, Date deadlines, int priority){
        super(task,mark,priority);
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
     * Setter of deadlines.
     * @param deadlines the new deadline date of the task.
     */
    public void setDeadlines(Date deadlines){
        this.deadlines = deadlines;
    }

    /**
     * Allows to snooze the deadline date
     */
    public void snoozeDeadline() {
        this.deadlines.snoozeLocalDateTime();
    }

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
        return super.toString() + " by: " + getDeadlines() + " [Priority: " + getPriority() + "]";
    }

    /**
     * Allow postpone the deadline of the deadline task.
     * With verification that the new deadline should be after the old one.
     * @param d Date d : the new deadline
     * @throws PostponeDeadlineException Exception caught when the new deadline is before the old one.
     */
    public void postponeDeadline(Date d) throws PostponeDeadlineException {
        if (d.getD().isBefore(this.deadlines.getD())){
            throw new PostponeDeadlineException();
        }
        else{
            this.deadlines = d;
        }
    }
}

