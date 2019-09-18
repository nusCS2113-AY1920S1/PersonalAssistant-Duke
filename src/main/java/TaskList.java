import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a task list used to manage the task objects within a task list.
 * @author Zhang Yue Han
 */
public class TaskList {

    /**
     * The task list which holds the task objects in an array known within the class.
     */
    protected ArrayList<Task> tasks;

    /**
     * Assigns the class array to take on the value of the latest updated list
     * @param list the list containing the latest list of tasks from the file
     */
    TaskList(ArrayList<Task> list) {
        tasks = list;
    }

    /**
     * Returns the size of the task list
     * @return the integer value of the number of tasks in the list
     */
    public int getSize() { return tasks.size(); }

    /**
     * Extracts the array list from this TaskList object
     * @return the array list with latest tasks
     */
    public ArrayList<Task> getList() { return tasks; }

    /**
     * Returns the task object at a specific index
     * @param index index of the task item to be returned
     * @return task object at the specific array index
     */
    public Task getTaskItem(int index) { return tasks.get(index); }

    /**
     * Returns a specific task in the file storage format
     * @param index index of the task object to be returned
     * @return data string format of the task object at specified index
     */
    public String getItemData(int index) { return tasks.get(index).toData(); }

    /**
     * Converts the entire task array into a string for storage
     * @return string with the contents of the task objects in the task array list
     */
    public String getArrayData() {
        String arrayString = "";
        for (Task task : tasks) {
            arrayString = arrayString + task.toData() + System.lineSeparator();
        }
        arrayString = arrayString.substring(0, arrayString.length() - 1);
        return arrayString;
    }

    /**
     * Calls the add function to add task object to list
     * @param t task object to be added
     */
    public void addItem(Task t) {
        tasks.add(t);
    }

    /**
     * Calls the remove function to remove task object from list
     * @param index the index of the task object in array to be removed
     */
    public void deleteItem(int index) {
        tasks.remove(index);
    }

    /**
     * Calls the function to mark the task at the specified index as done
     * @param index index of the task object in array to be removed
     */
    public void markItemDone(int index) { tasks.get(index).markAsDone(); }

    /**
     * Searches through the array list for words that match user description
     * @param phrase search phrase entered by the user
     * @return the array list of found items
     */
    public ArrayList<Task> findItem(String phrase) {
        ArrayList<Task> foundItems = new ArrayList<>();
        for (Task t : tasks) {
            String[] words = t.getDescription().split(" ");
            if (Arrays.asList(words).contains(phrase)) {
                foundItems.add(t);
            }
        }
        return foundItems;
    }

    public void snoozeItem(int index){
        tasks.get(index).getDate();
    }

}
