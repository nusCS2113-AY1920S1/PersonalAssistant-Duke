package Events.Storage;

import Events.EventTypes.Event;
import Events.EventTypes.Task;
import Events.Formatting.DateObj;
import Events.Formatting.Predicate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Allows for access to the list of tasks currently stored, and editing that list of tasks.
 * Does NOT contain any methods for reading/writing to savefile.
 */
public class TaskList {

    /**
     * list of Model_Class.Task objects currently stored.
     */
    private ArrayList<Task> taskArrayList;
    
    /**
	 * Filter type codes
	 */
    static final int DATE = 0;
    static final int TYPE = 1;

    protected int ONE_SEMESTER_DAYS = 16*7;

    /**
     * Creates new Model_Class.TaskList object.
     *
     * @param inputList list of strings containing all information extracted from save file
     */
    public TaskList(ArrayList<String> inputList) {
        taskArrayList = new ArrayList<Task>();
        for (String currLine : inputList) {
            boolean isDone = (currLine.substring(4,7).equals("✓"));
            if (currLine.charAt(1) == 'T') { //todo type task
                taskArrayList.add(new ToDo(currLine.substring(9), isDone));
            } else if (currLine.charAt(1) == 'E') { //event type task
                int posOfLine = currLine.indexOf("(at: ");
                taskArrayList.add(new Event(currLine.substring(9, posOfLine), currLine.substring(posOfLine + 5, currLine.length() - 1), isDone));
            } else if (currLine.charAt(1) == 'D') { //deadline type task
                int posOfLine = currLine.indexOf("(by: ");
                taskArrayList.add(new Deadline(currLine.substring(9, posOfLine), currLine.substring(posOfLine + 5, currLine.length() - 1), isDone));
            }
        }
    }

    /**
     * Checks for a clash, then adds a new task if possible.
     *
     * @param task Model_Class.Task object to be added
     * @return boolean signifying whether or not the task was added successfully. True if succeeded
     * and false if not
     */
    public boolean addTask(Task task) {
        if (task instanceof ToDo) {
            this.taskArrayList.add(task);
            return true;
        }
        else {
            Task clashTask = clashTask(task); //check the list for a schedule clash
            if (clashTask == null) { //null means no clash was found
                this.taskArrayList.add(task);
                return true;
            } else return false;
        }
    }

    /**
     * Adds recurring events to the list.
     *
     * @param event Event to be added as recursion.
     * @param period Period of the recursion.
     */
    public boolean addRecurringEvent(Event event, int period) {
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HHmm");
        SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
        DateObj taskDate = new DateObj(event.getDate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(taskDate.getJavaDate());
        for (int addTaskCount = 0; addTaskCount*period <= ONE_SEMESTER_DAYS; addTaskCount++) {
            String timeString = null;
            if (taskDate.getFormat() == 1) {
                timeString = format1.format(calendar.getTime());
            }
            else if (taskDate.getFormat() == 2) {
                timeString = format2.format(calendar.getTime());
            }
            this.taskArrayList.add(new Event(event.getDescription(), timeString));
            calendar.add(Calendar.DATE, period);
        }
        return true;
    }

    /**
     * Checks the list of tasks for any clashes with the newly added task. If
     * there is a clash, return a reference to the task, if not, return null.
     * @param task newly added task
     * @return task that causes a clash
     */
    private Task clashTask(Task task) {
        for (Task currTask : taskArrayList) {
            try {
                if (currTask.getDate().equals(task.getDate())) {
                    return currTask;
                }
            } catch (Exception e){
            }
        }
        return null;
    }

    /**
     * Deletes a task from the list.
     *
     * @param taskNo Index of task to be deleted
     */
    public void deleteTask(int taskNo) {
        this.taskArrayList.remove(taskNo);
    }

    /**
     * Gets list of Model_Class.Task objects stored
     *
     * @return Array of TaskLists containing all tasks.
     */
    public ArrayList<Task> getTaskArrayList() {
        return this.taskArrayList;
    }

    /**
     * Gets number of tasks stored.
     *
     * @return number of tasks stored
     */
    public int getNumTasks() {
        return taskArrayList.size();
    }

    /**
     * Gets a specific task using indexing.
     *
     * @param index Index of task to be extracted
     * @return Model_Class.Task object of specified task
     */
    public Task getTask(int index) {
        return taskArrayList.get(index);
    }

    /**
     * Gets the entire list of tasks stored in String format
     *
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
    
    /**
     * Gets a filtered list of tasks based on a predicate.
     * @return String containing the filtered list of tasks, separated by a newline.
     */
    public String filteredList(Predicate<Object> predicate, int filterCode) {
        String filteredTasks = "";
        int j = 1;
        for (int i = 0; i < taskArrayList.size(); ++i) {
            if (taskArrayList.get(i) == null) {
            	continue;
            } else if (filterCode == DATE) {
                if (taskArrayList.get(i) instanceof Event || taskArrayList.get(i) instanceof Deadline) {
                	if (!predicate.check(taskArrayList.get(i).getDateObj())) {
                		continue;
                	} 
                } else {
                	continue;
                }
            } else if (filterCode == TYPE) {
                if (!predicate.check(taskArrayList.get(i).getType())) {
                	continue;
                }
            } 
            filteredTasks += j + ". " + this.getTask(i).toString() + "\n";
            j++;
        }
        return filteredTasks;
    }
}
