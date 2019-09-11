import java.util.ArrayList;

/**
 * Allows for access to the list of tasks currently stored, and editing that list of tasks.
 * Does NOT contain any methods for reading/writing to savefile.
 */
public class TaskList {

    /**
     * list of Task objects currently stored.
     */
    private ArrayList<Task> taskArrayList;

    /**
     * Creates new TaskList object.
     * @param inputList list of strings containing all information extracted from save file
     */
    public TaskList(ArrayList<String> inputList) {
        taskArrayList = new ArrayList<Task>();
        for (String currLine : inputList){
            boolean isDone = (currLine.charAt(4) == '\u2713');
            if (currLine.charAt(1) == 'T') {
                taskArrayList.add(new ToDo(currLine.substring(7), isDone));
            } else if (currLine.charAt(1) == 'E') {
                int posOfLine = currLine.indexOf("(at: ");
                taskArrayList.add(new Event(currLine.substring(7, posOfLine), currLine.substring(posOfLine + 5, currLine.length() - 1), isDone));
            } else if (currLine.charAt(1) == 'D') {
                int posOfLine = currLine.indexOf("(by: ");
                taskArrayList.add(new Deadline(currLine.substring(7, posOfLine), currLine.substring(posOfLine + 5, currLine.length() - 1), isDone));
            }
        }
    }

    /**
     * Adds a new task to the list
     * @param task Task object to be added
     */
    public void addTask(Task task){
        this.taskArrayList.add(task);
    }

    /**
     * Deletes a task from the list.
     * @param taskNo Index of task to be deleted
     */
    public void deleteTask(int taskNo) {
        this.taskArrayList.remove(taskNo);
    }

    /**
     * Gets list of Task objects stored
     * @return Array<Task> containing all tasks.
     */
    public ArrayList<Task> getTaskArrayList(){
        return this.taskArrayList;
    }

    /**
     * Gets number of tasks stored.
     * @return number of tasks stored
     */
    public int getNumTasks(){
        return taskArrayList.size();
    }

    /**
     * Gets a specific task using indexing.
     * @param index Index of task to be extracted
     * @return Task object of specified task
     */
    public Task getTask(int index){
        return taskArrayList.get(index);
    }

    /**
     * Gets the entire list of tasks stored in String format
     * @return String containing all tasks, separated by a newline.
     */
    public String listOfTasks_String() {
        String allTasks = "";
        for (int i = 0; i < taskArrayList.size(); ++i) {
            if (taskArrayList.get(i) == null) continue;
            int j = i + 1;
            allTasks += j + ". " + this.getTask(i).toString() + "\n";
        }
        return allTasks;
    }
}
