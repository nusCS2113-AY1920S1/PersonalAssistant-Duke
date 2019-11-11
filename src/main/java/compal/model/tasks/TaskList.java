package compal.model.tasks;

import java.util.ArrayList;
import java.util.Comparator;


public class TaskList {

    private ArrayList<Task> arrlist;
    private TaskIdManager taskIdManager;

    /**
     * Constructs TaskList object.
     */
    public TaskList() {
        taskIdManager = new TaskIdManager();
    }

    public ArrayList<Task> getArrList() {
        return this.arrlist;
    }

    /**
     * Sets the arrlist to arrlist. Called after loading data from file.
     *
     * @param arrlist arraylist to set the arrlist
     */
    public void setArrList(ArrayList<Task> arrlist) {
        this.arrlist = arrlist;
        //make sure any user edits are brought over to the binary file as well
        taskIdManager.synchronizeTaskIds(this);
    }

    //@@author jaedonkey

    /**
     * Handles the adding of the tasks.
     * It tests for the task type, then parses it according to the correct syntax.
     * Used in parser.processCommands.
     *
     * @param task Task to be added to the list of tasks.
     */
    public void addTask(Task task) {
        //generate unique ID for task
        taskIdManager.generateAndSetId(task);
        arrlist.add(task);
    }

    //@@author jaedonkey

    /**
     * Returns a task that has an id value of id.
     */
    public Task getTaskById(int id) {
        //search for task with id of id
        for (Task t : arrlist) {
            if (t.getId() == id) {
                return t;
            }
        }
        throw null;
    }

    //@@author jaedonkey

    /**
     * Removes a task that has an id value of id.
     */
    public void removeTaskById(int id) {
        //search for task with id of id
        for (Task t : arrlist) {
            if (t.getId() == id) {
                arrlist.remove(t);
                break;
            }
        }
    }

    //@@author SholihinK

    /**
     * Sorts all the tasks in arrlist by date.
     *
     * @param arrlist sorted
     */
    public void sortTask(ArrayList<Task> arrlist) {
        Comparator<Task> compareByPriority = Comparator.comparing(Task::getDateObgMainDateAndStartOrEndTime)
            .thenComparing(Task::getStringMainOrTrailingDateAndEndTime).thenComparing(Task::getPriority);
        arrlist.sort(compareByPriority);
    }


}
