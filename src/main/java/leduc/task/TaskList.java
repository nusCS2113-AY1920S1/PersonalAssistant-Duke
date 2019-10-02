package leduc.task;

import leduc.Date;
import leduc.exception.ConflictDateException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the list of tasks.
 */
public class TaskList {
    private List<Task> tasks;

    /**
     * Constructor of tasks list.
     * @param tasks Arraylist of tasks.
     */
    public TaskList(List<Task> tasks){
        this.tasks = tasks;
    }

    /**
     * Returns the task in the position i of the tasks list.
     * @param i position of the task in the tasks list to returns.
     * @return the task in the position i of the tasks list.
     */
    public Task get(int i ){
        return this.tasks.get(i);
    }

    /**
     * Add a task to the tasks list.
     * @param t the task to add.
     */
    public void add(Task t ){
        this.tasks.add(t);
    }

    /**
     * Returns the size of the tasks list.
     * @return size of the tasks list.
     */
    public int size(){
        return this.tasks.size();
    }

    /**
     * Returns the task which is removed from the tasks list at the position i.
     * @param i the position of the task to remove in the tasks list.
     * @return the task which is removed from the tasks list at the position i.
     */
    public Task remove( int i){
        return this.tasks.remove(i);
    }

    /**
     * Returns the String of display of one element of the list of tasks.
     * @param index the position of the task to display in the tasks list.
     * @return the String of display of one element of the list of tasks.
     */
    public String displayOneElementList(int index){
        Task t = this.tasks.get(index);
        String result = "\t "+ (index+1) + ". " + t.toString() + "\n";
        return result;
    }

    /**
     * get the list
     * @return the task list
     */
    public ArrayList<Task> getList(){
        return (ArrayList<Task>) this.tasks;
    }

    /**
     * Verify if there are event that are in conflict with the date
     * @param date1 the start date
     * @param date2 the end date
     * @throws  ConflictDateException Exception thrown when the new event is in conflict with others event.
     */
    public void verifyConflictDate(Date date1, Date date2) throws ConflictDateException {
        ArrayList<Task> conflictTasks = new ArrayList<>();
        for (Task t : tasks){
            if(t.isEvent()){
                if(date1.getD().isAfter(((EventsTask)t).getDateFirst().getD()) && date1.getD().isBefore(((EventsTask)t).getDateSecond().getD())){
                    conflictTasks.add(t);
                }
                else if(date2.getD().isAfter(((EventsTask)t).getDateFirst().getD()) && date2.getD().isBefore(((EventsTask)t).getDateSecond().getD())){
                    conflictTasks.add(t);
                }
                else if(date1.getD().isBefore(((EventsTask)t).getDateFirst().getD()) && date2.getD().isAfter(((EventsTask)t).getDateSecond().getD())){
                    conflictTasks.add(t);
                }
            }
        }
        if(!conflictTasks.isEmpty()){
            throw new ConflictDateException(conflictTasks);
        }
    }
}
