package duke.tasklist;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;

import duke.exception.DukeException;
import duke.task.Task;
import duke.ui.Ui;

/**
 * Represents the data structure containing all tasks added to the task manager
 * Uses java.util.ArrayList as the main container
 */
public class TaskList {
    private ArrayList<Task> taskList;

    /**
     * Constructor for duke.tasklist.TaskList
     * this is to initialise an EMPTY duke.tasklist.TaskList so it takes no inputs
     */
    public TaskList() {
        taskList = new ArrayList<Task>();
    }

    /**
     * Constructor for duke.tasklist.TaskList
     * this is to initialise a duke.tasklist.TaskList with an ArrayList of Tasks
     * this facilitates the loading of saved duke.task.Task data
     *
     * @param tasks an ArrayList of Tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        taskList = tasks;
        for (int i = 0; i < taskList.size(); i++) {
            Task t = taskList.get(i);
            t.updateDone();
        }
    }

    public TaskList(TaskList list, Optional<String> filter) {
        taskList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Task t = list.get(i);
            if (t.getFilter().equals(filter)) {
                taskList.add(t);
            }
        }
    }

    public ArrayList<Task> getList() {
        return taskList;
    }

    /**
     * returns the number of tasks
     *
     * @return size the number of tasks in the duke.tasklist.TaskList now
     */
    public int size() {
        return taskList.size();
    }

    public Task get(int index) {
        return taskList.get(index);
    }


    public Task get(Optional<String> filter, int index) throws DukeException {
        int i = reduceFilter(filter, index);
        return taskList.get(i);
    }

    /**
     * adds a duke.task.Task to the back of the duke.tasklist.TaskList
     *
     * @param task the duke.task.Task to be added
     */
    public void add(Task task) {
        taskList.add(task);
        System.out.println("You have added this task:");
        System.out.println(task.getDescription());
        int taskCount = taskList.size();
        if (taskCount == 1) {
            System.out.println("Now you have " + taskCount + " task in the list.");
        } else {
            System.out.println("Now you have " + taskCount + " tasks in the list.");
        }
    }

    /**
     * Searches for any duke.task.Task objects that contain the keyword entered
     * if there are at least one duke.task.Task objects fulfilling the criteria
     * will print out a list of these duke.task.Task objects for the user to see
     * otherwise will notify the user that no duke.task.Task objects have the keyword inside
     *
     * @param keyword the keyword which the user is searching for Tasks with
     * @param ui      the user interface class which deals with user interactions
     */
    public void find(String keyword, Ui ui) {
        ArrayList<Task> temp = new ArrayList<>();
        for (Task t : taskList) {
            if (t.getDescription().contains(keyword)) {
                temp.add(t);
            }
        }
        if (temp.size() == 0) {
            ui.showLine("There are no matching tasks in your list :-(");
        } else {
            ui.showLine("Here are the matching tasks in your list:");
            for (int i = 0; i < temp.size(); i++) {
                ui.showLine((i + 1) + "." + temp.get(i));
            }
        }
    }

    /**
     * removes the duke.task.Task at index i
     *
     * @param i the index at which the duke.task.Task should be removed
     */
    public void remove(int i) {
        taskList.remove(i);
    }

    public void remove(Optional<String> filter, int index) throws DukeException {
        int counter = -1;
        if (filter.isPresent()) {
            for (int i = 0; i < taskList.size(); i++) {
                Task t = taskList.get(i);
                if (t.getFilter().equals(filter)) {
                    counter++;
                }
                if (counter == index - 1) {
                    taskList.remove(i);
                    return;
                }
            }
        } else {
            taskList.remove(index - 1);
            return;
        }
        throw new DukeException("Index not found");
    }

    public TaskList priorityView() {
        ArrayList<Task> temp = new ArrayList<>();
        for (Task t : taskList) {
            temp.add(t);
        }
        temp.sort((a, b) -> a.getPriorityLevel() < b.getPriorityLevel() ? 1 : -1);
        return new TaskList(temp);
    }

    public TaskList dayView() {
        LocalDate currDate = LocalDate.now();
        ArrayList<Task> temp = new ArrayList<>();
        for (Task t : taskList) {
            if (t.hasDateTime()) {
                LocalDateTime taskDate = t.getDateTime();
                if (ChronoUnit.DAYS.between(currDate, taskDate) == 0) {
                    temp.add(t);
                }
            }
        }
        return new TaskList(temp);
    }

    public TaskList weekView() {
        LocalDate currDate = LocalDate.now();
        ArrayList<Task> temp = new ArrayList<>();
        for (Task t : taskList) {
            if (t.hasDateTime()) {
                LocalDateTime taskDate = t.getDateTime();
                if (ChronoUnit.DAYS.between(currDate, taskDate) < 7
                        && ChronoUnit.DAYS.between(currDate, taskDate) > -1) {
                    temp.add(t);
                }
            }
        }
        return new TaskList(temp);
    }

    public TaskList undoneView() {
        ArrayList<Task> list = new ArrayList<>();
        for (Task t : taskList) {
            if (t.getStatusIcon().equals("N")) {
                list.add(t);
            }
        }
        return new TaskList(list);
    }

    public void insert(Optional<String> filter, int index, Task t) throws DukeException {
        int i = reduceFilter(filter, index);
        taskList.add(i, t);
    }

    public void set(Optional<String> filter, int index, Task t) throws DukeException {
        int i = reduceFilter(filter, index);
        taskList.remove(i);
        taskList.add(i, t);
    }

    private int reduceFilter(Optional<String> filter, int index) throws DukeException {
        int counter = -1;
        if (filter.isPresent()) {
            for (int i = 0; i < taskList.size(); i++) {
                Task t = taskList.get(i);
                if (t.getFilter().equals(filter)) {
                    counter++;
                }
                if (counter == index - 1) {
                    return i;
                }
            }
        } else {
            return index - 1;
        }
        throw new DukeException("Index not found");
    }
}
