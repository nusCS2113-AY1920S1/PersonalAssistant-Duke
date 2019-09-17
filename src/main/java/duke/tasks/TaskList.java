package duke.tasks;

import duke.parsers.DateParser;

import java.util.ArrayList;
import java.util.Date;

/**
 * TaskList is a public class that represents the list of tasks under duke.
 * A TaskList object encapsulates the ArrayList of tasks
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * This is the constructor of TaskList object.
     * @param tasks the array list of tasks to be assigned
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * This is the constructor of TaskList object if there is no argument.
     * The TaskList object will initialise a new empty arraylist of task
     */
    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    /*
    void add(Task newTask) { (this.tasks).add(newTask);}
    void add(ToDo newToDo) {
        (this.tasks).add(newToDo);
    }
    void add(Deadline newDeadline) {
        (this.tasks).add(newDeadline);
    }
    void add(Event newEvent) {
        (this.tasks).add(newEvent);
    }
     */

    /**
     * This function is used to delete the task of a particular index.
     * @param index the index of task to be deleted
     */
    public void delete(int index) {
        this.tasks.remove(index - 1);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns list of tasks that happen on specified input date
     * @param taskDate input date to check tasks against
     * @return list of tasks that happen on the same day as taskDate
     */
    public ArrayList<Task> getSameDayTasks(Date taskDate) {
        ArrayList<Task> sameDayTasks = new ArrayList<Task>();
        for (Task task : tasks) {
            if (DateParser.isSameDayMonthYear(taskDate, task.getDate().getTime())) {
                sameDayTasks.add(task);
            }
        }

        return sameDayTasks;
    }

    public String toString() {
        if (tasks.size() == 0) {
            return "No tasks available";
        }

        String outStr = "";
        for (Task task : tasks) {
            outStr += task.toString() + "\n";
        }
        return outStr;
    }

}
