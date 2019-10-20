package leduc.task;

import leduc.Date;
import leduc.exception.DateComparisonEventException;


/**
 * Represents a Event Task.
 */
public class EventsTask extends Task {
    private String tag; // [E]
    private Date dateFirst;
    private Date dateSecond;

    /**
     * Constructor of leduc.task.EventsTask. The task is not done by default.
     * And the priority is 5 by default.
     * @param task String representing the description of the Task.
     * @param dateFirst the start date of the period of the event.
     * @param dateSecond the end date of the period of the event.
     */
    public EventsTask(String task, Date dateFirst, Date dateSecond) {
        super(task);
        this.tag = "[E]";
        this.dateFirst = dateFirst;
        this.dateSecond = dateSecond;
    }

    /**
     * Constructor of leduc.task.EventsTask. The task is not done by default.
     * @param task String representing the description of the Task.
     * @param dateFirst the start date of the period of the event.
     * @param dateSecond the end date of the period of the event.
     * @param priority the priority of the event.
     */
    public EventsTask(String task, Date dateFirst, Date dateSecond, int priority){
        super(task,priority);
        this.dateFirst= dateFirst;
        this.dateSecond = dateSecond;
    }

    /**
     * Constructor of leduc.task.EventsTask. The task could be done or not depending on the parameter given.
     * the priority is 5 by default.
     * @param task String representing the description of the Task.
     * @param mark represent if the task is done or not.
     * @param dateFirst the start date of the period of the event.
     * @param dateSecond the end date of the period of the event.
     */
    public EventsTask(String task, String mark, Date dateFirst, Date dateSecond) {
        super(task,mark);
        this.tag = "[E]";
        this.dateFirst = dateFirst;
        this.dateSecond =dateSecond;
    }




    /**
     * Constructor of leduc.task.EventsTask. The task could be done or not depending on the parameter given.
     * @param task String representing the description of the Task.
     * @param mark represent if the task is done or not.
     * @param dateFirst the start date of the period of the event.
     * @param dateSecond the end date of the period of the event.
     * @param priority the priority of the event.
     */
    public EventsTask(String task, String mark, Date dateFirst, Date dateSecond, int priority) {
        super(task,mark,priority);
        this.tag = "[E]";
        this.dateFirst = dateFirst;
        this.dateSecond =dateSecond;
    }

    /**
     * Getter of Tag ( [T] ).
     * @return String : [T]
     */
    public String getTag(){ return this.tag;}

    /**
     * Getter of start date.
     * @return the start date of the task.
     */
    public Date getDateFirst(){ return this.dateFirst;}

    /**
     * Getter of end date.
     * @return the end date of the task.
     */
    public Date getDateSecond(){ return this.dateSecond; }
    /**
     * to know if whether is an event task of not
     * @return true
     */
    @Override
    public boolean isEvent(){
        return true;
    }
    /**
     * visualize a event task
     * @return the string format to see a event task
     */
    public String toString(){
        return super.toString() + " at: " + getDateFirst() + " - " + getDateSecond() + " [Priority: " + getPriority() + "]";
    }

    /**
     * Allow reschedule the period of the event task.
     * With verification that the second date should be after the first one.
     * @param d1 the first date.
     * @param d2 the second date.
     * @throws DateComparisonEventException Exception caught when the second date is before the first one.
     */
    public void reschedule(Date d1, Date d2) throws DateComparisonEventException {
        if (d2.getD().isBefore(d1.getD())){
            throw new DateComparisonEventException();
        }
        else{
            this.dateFirst = d1;
            this.dateSecond = d2 ;
        }
    }
}

