package task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TaskList {
    /**
     * Stores the current list of task of the user
     */
    private List<Task> taskList;

    /**
     * Constructs a new taskList object.
     * @param taskList The list of task to be added.
     */
    public TaskList(List<Task> taskList){
        this.taskList = taskList;
    }

    /**
     * Returns the list of tasks in the task list.
     * @return The list of tasks.
     */
    public List<Task> getTaskList(){
        return taskList;
    }

    /**
     * Add the given task into the task list.
     * @param task The task to be added.
     */
    public void addTask(Task task){
        taskList.add(task);
    }

    /**
     * Retrieve the task at the given index of the task list.
     * @param index The index of the task in the list.
     * @return The task at the given index.
     */
    public Task getTask(int index){
        return taskList.get(index);
    }

    /**
     * Modify the value of the task at the given index in the list.
     * @param index The index of the task in the list
     * @param task The task with modified values
     */
    public void modifyTask(int index, Task task){
        taskList.set(index, task);
    }

    /**
     * Removes the task at the given index of the task list.
     * @param index The index of the task in the list
     */
    public void deleteTask(int index){
        taskList.remove(index);
    }

    /**
     * Get the current number of tasks in the task list.
     * @return The number of tasks in the list.
     */
    public int getTaskListSize(){
        return taskList.size();
    }

    /**
     * Creates and populate a corresponding task object given its type.
     *
     * @param type The type of task to be created
     * @param description The description of the task
     * @return The task object with its corresponding values
     */
    public Task createTask(String type, String description) {
        String[] info;

        if (description.length() == 0){
            System.out.println("☹ OOPS!!! The description of " + type + " cannot be empty");
            return null;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");
            if (type.equals("todo")) {
                return new Todo(description);
            } else if (type.equals("deadline")) {
                info = description.split("/by");
                Date date = sdf.parse(info[1].trim());
                return new Deadline(info[0].trim(), date);
            } else if (type.equals("event")) {
                info = description.split("/at");
                Date date = sdf.parse(info[1].trim());
                return new Event(info[0].trim(), date);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("☹ OOPS!!! The date/time of a " + type + " cannot be empty");
        } catch (ParseException e) {
            System.out.println("☹ OOPS!!! The format of date/time is \"dd/mm/yyyy hhmm\"");
        }
        return null;
    }
}
