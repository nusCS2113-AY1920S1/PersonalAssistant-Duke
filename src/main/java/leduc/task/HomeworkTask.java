package leduc.task;
import leduc.Date;
import leduc.exception.PostponeHomeworkException;

/**
 * Represents a homework Task.
 */
public class HomeworkTask extends Task {
    private String tag ="[H]" ;
    private Date deadlines;

    /**
     * Constructor of leduc.task.Homework. The task is not done by default.
     * And the priority is 5 by default.
     * @param task String representing the description of the Task.
     * @param deadlines the deadline of the task.
     */
    public HomeworkTask(String task, Date deadlines){
        super(task);
        this.deadlines = deadlines;
    }

    /**
     * Constructor of leduc.task.HomeworkTask. The task is not done by default.
     * @param task  String representing the description of the Task.
     * @param deadlines the deadline of the task.
     * @param priority the priority of the Homework task.
     */
    public HomeworkTask(String task, Date deadlines, int priority){
        super(task,priority);
        this.deadlines = deadlines;
    }

    /**
     * Constructor of leduc.task.HomeworkTask. The task could be done or not depending on the parameter given.
     * And the priority is 5 by default.
     * @param task  String representing the description of the Task.
     * @param mark represent if the task is done or not.
     * @param deadlines the deadline of the task.
     */
    public HomeworkTask(String task, String mark, Date deadlines){
        super(task,mark);
        this.deadlines = deadlines;
    }


    /**
     * Constructor of leduc.task.HomeworkTask. The task could be done or not depending on the parameter given.
     * @param task  String representing the description of the Task.
     * @param mark represent if the task is done or not.
     * @param deadlines the deadline of the task.
     * @param priority the priority of the deadline task.
     */
    public HomeworkTask(String task, String mark, Date deadlines, int priority){
        super(task,mark,priority);
        this.deadlines = deadlines;
    }

    /**
     * Getter of Tag ( [H] ).
     * @return String : [H]
     */
    public String getTag(){ return this.tag;}

    /**
     * Getter of homework.
     * @return the deadline date of the task.
     */
    public Date getDeadlines(){ return this.deadlines;}

    /**
     * Setter of homework.
     * @param deadlines the new deadline date of the task.
     */
    public void setDeadlines(Date deadlines){
        this.deadlines = deadlines;
    }

    /**
     * Allows to snooze the homework deadline date
     */
    public void snoozeDeadline() {
        this.deadlines.snoozeLocalDateTime();
    }

     /**
     * to know if whether is a homework task of not
     * @return true
     */
    @Override
    public boolean isHomework(){
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
     * Allow postpone the deadline of the homework task.
     * With verification that the new deadline should be after the old one.
     * @param date Date date : the new deadline
     * @throws PostponeHomeworkException Exception caught when the new deadline is before the old one.
     */
    public void postponeHomework(Date date) throws PostponeHomeworkException {
        if (date.getDate().isBefore(this.deadlines.getDate())){
            throw new PostponeHomeworkException();
        }
        else{
            this.deadlines = date;
        }
    }
}

