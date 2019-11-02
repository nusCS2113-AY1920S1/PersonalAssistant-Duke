package compal.model.tasks;


import java.util.ArrayList;
import java.util.Date;


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

    /**
     * Remove a task from the arrayList.
     */
    public void removeTaskByIndex(int index) {
        arrlist.remove(index);
    }

    //@@author jaedonkey
    /**
     * Clears the current id for future tasks to use (used in deletion of tasks).
     *
     * @param id task id
     */
    public void unsetId(int id) {
        taskIdManager.clearId(id);
        System.out.println("TaskList:LOG:" + id + " unset");
    }

    public ArrayList<Task> returnTaskList() {
        return this.arrlist;
    }

    //@@author SholihinK
    /**
     * Sorts all the tasks in arrlist by date.
     *
     * @param arrlist sorted
     */
    public void sortTask(ArrayList<Task> arrlist) {
        boolean sorted = false;
        int arraySize = arrlist.size();
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < arraySize - 1; i++) {
                Date task1Date = arrlist.get(i).getMainDate();
                Date task2Date = arrlist.get(i + 1).getMainDate();
                Task.Priority priority1 = arrlist.get(i).getPriority();
                Task.Priority priority2 = arrlist.get(i + 1).getPriority();

                if (task1Date.after(task2Date)) {
                    Task temp = arrlist.get(i);
                    arrlist.set(i, arrlist.get(i + 1));
                    arrlist.set(i + 1, temp);
                    sorted = false;
                }

                if (task1Date.equals(task2Date)) {
                    Date task1StartTime = arrlist.get(i).getStartTime();
                    Date task2StartTime = arrlist.get(i + 1).getStartTime();

                    Date task1EndTime = arrlist.get(i).getEndTime();
                    Date task2EndTime = arrlist.get(i + 1).getEndTime();

                    boolean prio1IsLow = priority1.equals(Task.Priority.low)
                        && (priority2.equals(Task.Priority.high)
                        || priority2.equals(Task.Priority.medium));


                    boolean prio1isMed = priority1.equals(Task.Priority.medium)
                        && priority2.equals(Task.Priority.high);


                    if ((task1StartTime == null && task2StartTime == null)
                        || (task1StartTime == null && task2StartTime != null)
                        || (task1StartTime != null && task2StartTime == null)) {
                        if (task1EndTime.after(task2EndTime)) {
                            Task temp = arrlist.get(i);
                            arrlist.set(i, arrlist.get(i + 1));
                            arrlist.set(i + 1, temp);
                            sorted = false;
                        }

                        if (task1EndTime.equals(task2EndTime)) {
                            if (prio1IsLow) {
                                Task temp = arrlist.get(i);
                                arrlist.set(i, arrlist.get(i + 1));
                                arrlist.set(i + 1, temp);
                                sorted = false;
                            } else if (prio1isMed) {
                                Task temp = arrlist.get(i);
                                arrlist.set(i, arrlist.get(i + 1));
                                arrlist.set(i + 1, temp);
                                sorted = false;
                            }
                        }
                    } else if (task1StartTime != null && task2StartTime != null) {
                        if (task1StartTime.after(task2StartTime)) {
                            Task temp = arrlist.get(i);
                            arrlist.set(i, arrlist.get(i + 1));
                            arrlist.set(i + 1, temp);
                            sorted = false;
                        } else if (task1StartTime.equals(task2StartTime) && task1EndTime.after(task1EndTime)) {
                            Task temp = arrlist.get(i);
                            arrlist.set(i, arrlist.get(i + 1));
                            arrlist.set(i + 1, temp);
                            sorted = false;
                        }

                        if (task1StartTime.equals(task2StartTime) && task1EndTime.equals(task2EndTime)) {
                            if (prio1IsLow) {
                                Task temp = arrlist.get(i);
                                arrlist.set(i, arrlist.get(i + 1));
                                arrlist.set(i + 1, temp);
                                sorted = false;
                            } else if (prio1isMed) {
                                Task temp = arrlist.get(i);
                                arrlist.set(i, arrlist.get(i + 1));
                                arrlist.set(i + 1, temp);
                                sorted = false;
                            }
                        }
                    }
                }
            }
        }
    }


}
