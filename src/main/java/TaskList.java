import java.util.ArrayList;

/**
 * To keep track of the list of task input by user.
 */
public class TaskList {
    protected ArrayList<Task> list;

    /**
     * Creates a TaskList object.
     */
    public TaskList(){
        this.list = new ArrayList<>();
    }

    /**
     * Retrieve the ArrayList from the TaskList object.
     * @return This returns the ArrayList from the TaskList object
     */
    public ArrayList<Task> getList() {
        return list;
    }

    /**
     * This method adds a Task object into the ArrayList.
     * @param task Task object to be added
     */
    public void addTask(Task task){
        this.list.add(task);
    }

    /**
     * This method removes the task from the ArrayList.
     * @param index Index in the ArrayList to remove the Task object from
     */
    public void removeTask(int index){
        this.list.remove(index);
    }

    /**
     * This method retrieves the current index in the ArrayList.
     * @param index Index in the ArrayList to retrieve the Task object from
     * @return This returns the Task object that was retrieved
     */
    public Task getTask(int index){
        return this.list.get(index);
    }

    /**
     * This method converts a specific Task object from the ArrayList to a string.
     * @param index Index in the ArrayList to retrieve the Task object from
     * @return This returns the string of the Task object that was retrieved
     */
    public String taskToString(int index){
        return list.get(index).toString();
    }

    /**
     * This method marks a specific Task object in the ArrayList as done.
     * @param index Index in the ArrayList to retrieve the Task object from
     */
    public void markAsDone(int index){
        this.list.get(index).setDone(true);
    }

    /**
     * This method retrieves the size of the ArrayList.
     * @return This returns the size of the ArrayList
     */
    public int taskListSize(){
        return list.size();
    }

    /**
     * This method finds the tasks in the ArrayList that contains the keyword.
     * @param key The keyword input by the user
     * @return This returns a TaskList object containing all the tasks with the keyword
     */
    public TaskList findTask(String key) {
        TaskList temp = new TaskList();
        for (Task task : list) {
            if (task.getDescription().contains(key)) {
                temp.addTask(task);
            }
        }
        return temp;
    }
}
