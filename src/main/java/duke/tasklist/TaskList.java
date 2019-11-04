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
    private int longestFilter;

    /**
     * Constructor for duke.tasklist.TaskList
     * this is to initialise an EMPTY duke.tasklist.TaskList so it takes no inputs
     */
    public TaskList() {
        taskList = new ArrayList<Task>();
        longestFilter = 0;
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
        longestFilter = 0;
        for (int i = 0; i < taskList.size(); i++) {
            Task t = taskList.get(i);
            t.updateDone();
            if (t.getFilter().isPresent()) {
                int filterLength = t.getFilter().get().length();
                longestFilter = Math.max(longestFilter, filterLength);
            }

        }
    }

    /**
     * Constructor for duke.tasklist.TaskList
     * this is to initalise a duke.tasklist.TaskList with an ArrayList of Tasks which fulfil the filter predicate
     * tasks which do not fulfil the predicate will not be present in this TaskList
     *
     * @param list   the main TaskList containing all of the user's tasks
     * @param filter the filter predicate for each task
     */
    public TaskList(TaskList list, Optional<String> filter) throws DukeException {
        taskList = new ArrayList<>();
        longestFilter = filter.get().length();
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

    public ArrayList<Task> getList(Optional<String> filter) {
        ArrayList<Task> temp = new ArrayList<Task>();
        for (int i = 0; i < taskList.size(); i++) {
            Task t = taskList.get(i);
            if (t.getFilter().equals(filter)) {
                temp.add(t);
            }
        }
        return temp;
    }

    /**
     * returns the number of tasks
     *
     * @return size the number of tasks in the duke.tasklist.TaskList now
     */
    public int size() {
        return taskList.size();
    }

    public Task get(int index) throws DukeException {
        if (index < 0) {
            throw new DukeException("Please enter a positive index!");
        }
        if (index < taskList.size()) {
            return taskList.get(index);
        }
        throw new DukeException("Please enter valid task index!");
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
        int filterLength = task.getFilter().isPresent() ? task.getFilter().get().length() : 0;
        longestFilter = Math.max(filterLength, longestFilter);
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
     * Removes a task at a certain index of the TaskList.
     * If a filter is given, then the index will be based on the filtered list that was printed to the user
     *
     * @param filter the filter predicate for each task
     * @param index  the index of the task as seen by the user in the TaskList printed
     */
    public void remove(Optional<String> filter, int index) throws DukeException {
        Task t = get(filter, index);
        int filterLength = t.getFilter().isPresent() ? t.getFilter().get().length() : 0;
        taskList.remove(reduceFilter(filter, index));
        if (filterLength == longestFilter) {
            int currLongest = 0;
            for (Task task : taskList) {
                int currFilterLength = task.getFilter().isPresent() ? task.getFilter().get().length() : 0;
                currLongest = Math.max(currLongest, currFilterLength);
            }
            longestFilter = currLongest;
        }
    }

    /**
     * Creates a TaskList which has tasks sorted by priority level from high to low
     *
     * @return TaskList sorted by priority
     */
    public TaskList priorityView() {
        ArrayList<Task> temp = new ArrayList<>();
        for (Task t : taskList) {
            temp.add(t);
        }
        temp.sort((a, b) -> a.getPriorityLevel() < b.getPriorityLevel() ? 1 : -1);
        return new TaskList(temp);
    }

    /**
     * Creates a TaskList which has tasks with datetime attributes equal to the current local day
     *
     * @return TaskList containing tasks of the current day
     */
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
        return new TaskList(temp).undoneView();
    }

    /**
     * Creates a TaskList which as tasks with datetime attributes within the next week.
     *
     * @return TaskList containing tasks within the next week
     */
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
        return new TaskList(temp).undoneView();
    }

    /**
     * Creates a TaskList containing only undone tasks
     *
     * @return TaskList of undone tasks
     */
    public TaskList undoneView() {
        ArrayList<Task> list = new ArrayList<>();
        for (Task t : taskList) {
            if (t.getStatusIcon().equals("N")) {
                list.add(t);
            }
        }
        return new TaskList(list);
    }

    /**
     * Inserts a task at the given index. If there is a filter given, the index of the insertion will be converted
     * to its corresponding index on the actual TaskList.
     *
     * @param filter filter for each task
     * @param index  index of TaskList where task will be inserted
     * @param t      new Task to be inserted at index
     * @throws DukeException if an invalid index is given
     */
    public void insert(Optional<String> filter, int index, Task t) throws DukeException {
        if (index == taskList.size()) {
            taskList.add(index, t);
        } else {
            int i = reduceFilter(filter, index);
            taskList.add(i, t);
        }
    }

    /**
     * Replaces a task at the given index with a new given task. If there is a filter given, the index of the task
     * will be converted to its corresponding index on the actual TaskList.
     *
     * @param filter filter for each task
     * @param index  index of TaskList which user saw corresponds to desired task
     * @param t      new Task to replace original at index
     * @throws DukeException if an invalid index is given
     */
    public void set(Optional<String> filter, int index, Task t) throws DukeException {
        remove(filter, index);
        insert(filter, index, t);
    }

    /**
     * If the user defines a filter, it means the user input an index corresponding to a TaskList with the filter
     * applied to it. This method will then convert the given index to the actual corresponding index of the same
     * task in the TaskList. If there is no filter, then the actual index is given so nothing needs to be changed.
     *
     * @param filter filter predicate for each task
     * @param index  index of TaskList which user saw corresponds to desired task
     * @return integer value of the actual index of the task in the TaskList
     * @throws DukeException if invalid index is given
     */
    private int reduceFilter(Optional<String> filter, int index) throws DukeException {
        int counter = -1;
        if (index < 0) {
            throw new DukeException("Please enter a positive index!");
        }
        if (filter.isPresent()) {
            for (int i = 0; i < taskList.size(); i++) {
                Task t = taskList.get(i);
                if (t.getFilter().equals(filter)) {
                    counter++;
                }
                if (counter == index) {
                    return i;
                }
            }
        } else {
            if (index < taskList.size()) {
                return index;
            }
        }
        throw new DukeException("Please enter a valid task index!");
    }

    public int getLongestFilter() {
        return longestFilter;
    }
}
