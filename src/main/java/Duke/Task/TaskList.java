package Duke.Task;

import java.util.ArrayList;

public class TaskList extends ArrayList<Task> {

    /**
     * Get the task for index based on 1
     * @param index the index of task to be queried, index starts from 1
     * @return The task with index starting from 0
     */
    private Task getTask(int index){
        return this.get(index - 1);
    }

    /**
     * Delete Task based on user input
     * @param index the index of task to be deleted, index starts from 1
     * @return The task that is deleted
     */
    public Task deleteTask(int index){

        return this.remove(index-1);
    }

    /**
     *
     * @param index The index of task to check if it is already completed, index starts from 1
     * @return {@code true} The task has been marked as completed
     *          {@code false} The task has not been mark as completed
     */
    public boolean isCompletedTask(int index){
        return getTask(index).isCompleted();
    }

    /**
     * Set Task as done based on user input
     * @param index the index of task to be set as done, index starts from 1
     * @return The task that is completed and set as done
     */
    public Task doneTask(int index){
        Task refTask = this.getTask(index);
        refTask.markAsDone();

        return refTask;
    }

    /**
     * Finds all the task that contains the String and append task to new arraylist
     * @param item String that contains the item user wants to find
     * @return New ArrayList with task that contains the String user input
     */
    public TaskList findTask(String item){
        TaskList foundTask = new TaskList();
        for(Task i : this){
            if(i.getDescription().contains(item))
                foundTask.add(i);
        }

        return foundTask;
    }


}
