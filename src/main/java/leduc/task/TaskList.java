package leduc.task;

import leduc.task.DeadlinesTask;
import leduc.task.EventsTask;
import leduc.task.Task;

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
        String result = "\t "+ (index+1) + ". " + t.getTag() +t.getMark() + " " +t.getTask();
        if (t instanceof DeadlinesTask){
            result +=  " by:" + ((DeadlinesTask) t).getDeadlines() + "\n";
        }
        else if ( t instanceof EventsTask){
            result +=  " at:" +
                    ((EventsTask) t).getDateFirst() + " - " + ((EventsTask) t).getDateSecond() + "\n";
        }
        else {
            result += "\n";
        }
        return result;
    }
}
