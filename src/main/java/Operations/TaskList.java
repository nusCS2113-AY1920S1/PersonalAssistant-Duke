package Operations;

import CustomExceptions.RoomShareException;
import Enums.ExceptionType;
import Enums.Priority;
import Enums.SortType;
import Enums.TimeUnit;
import Model_Classes.Assignment;
import Model_Classes.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * A class to perform operations on the task list in Duke
 */
public class TaskList {
    private static ArrayList<Task> tasks;
    private static SortType sortType = SortType.priority;
    public ArrayList<Task> overdueList = new ArrayList<>();

    /**
     * Constructor for the TaskList class.
     * takes in an ArrayList as the list of tasks to be operated on.
     * @param tasks ArrayList of Task objects to be operated on.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a new task into the task list
     * @param newTask Task object to be added into the list of tasks
     */
    public void add(Task newTask) {
        tasks.add(newTask);
        sortTasks();
    }

    /**
     * Deletes a task from the list. Task to be deleted is specified by the index that is input into this method
     * Will not perform any operations if the index does not exist in the list.
     * @param index Index of task in the list to be deleted
     * @param deletedList temporary storage list for the deleted items so they can be restored
     * @throws RoomShareException If the index cannot be found in the list of tasks.
     */
    public void delete(int[] index, TempDeleteList deletedList) throws RoomShareException {
        int[] idx = index.clone();
        if (idx.length == 1) {
            if (idx[0] < 0 || idx[0] >= tasks.size()) {
                throw new RoomShareException(ExceptionType.outOfBounds);
            }
            deletedList.add(tasks.get(idx[0]));
            tasks.remove(idx[0]);
        }
        else {
            if (idx[0] < 0 || idx[0] >= tasks.size() || idx[1] < 0 || idx[1] >= tasks.size()) {
                throw new RoomShareException(ExceptionType.outOfBounds);
            }
            for (int i = idx[0]; idx[1] >= idx[0]; idx[1]--) {
                deletedList.add(tasks.get(i));
                tasks.remove(i);
            }
        }
    }
    /**
     * Lists out all tasks in the current list in the order they were added into the list.
     * shows all information related to the tasks
     * hides completed tasks
     * @throws RoomShareException when the list is empty
     */
    public void list() throws RoomShareException {
        sortTasks();
        if (tasks.size() != 0) {
            int listCount = 1;
            for(int i=0; i<tasks.size(); i++) {
                if (new Date().after(tasks.get(i).getDate())) {
                    tasks.get(i).setOverdue(true);
                    this.overdueList.add(tasks.get(i));
                    tasks.remove(i);
                }
            }

            for (Task output : tasks) {
                if (!output.getDone()) {
                    Priority priority = output.getPriority();
                    String priorityLVL;
                    if (priority.equals(Priority.low)) {
                        priorityLVL = " *";
                    } else if (priority.equals(Priority.medium)) {
                        priorityLVL = " **";
                    } else {
                        priorityLVL = " ***";
                    }
                  
                    if (!output.getDone() && !output.getOverdue()) {
                        System.out.println("\t" + listCount + ". " + output.toString() + priorityLVL);
                        if (output instanceof Assignment && !(((Assignment) output).getSubTasks() == null)) {
                            ArrayList<String> subTasks = ((Assignment) output).getSubTasks();
                            for (String subtask : subTasks) {
                                System.out.println("\t" + "\t" + "- " + subtask);
                            }
                        }
                    }
                    listCount += 1;
                }
            }
        } else {
            throw new RoomShareException(ExceptionType.emptyList);
        }
    }

    /**
     * Lists out completed tasks in the list
     * @throws RoomShareException
     */
    public void showCompleted() throws RoomShareException {
        sortTasks();
        System.out.println("Completed Tasks:");
        if( tasks.size() != 0 ){
            int listCount = 1;
            for (Task output : tasks) {
                if( output.getDone() ) {
                    System.out.println("\t" + listCount + ". " + output.toString());
                    if( output instanceof Assignment && !(((Assignment) output).getSubTasks() == null) ) {
                        ArrayList<String> subTasks = ((Assignment) output).getSubTasks();
                        for (String subtask : subTasks) {
                            System.out.println("\t" + "\t" + "- " + subtask);
                        }
                    }
                }
                listCount += 1;
            }
        } else {
            throw new RoomShareException(ExceptionType.emptyList);
        }
    }

    /**
     * Adds the overdue tasks that a currently in the task list to another task list
     * which contains stores tasks that are overdue.
     * @return ArrayList of tasks containing tasks that are overdue.
     * @throws RoomShareException when the list is empty.
     */
    public ArrayList<Task> getOverdueList() throws RoomShareException {
        if( tasks.size() != 0 ){
            for (Task output : tasks) {
                if(new Date().after(output.getDate())) {
                    this.overdueList.add(output);
                }
            }
        } else {
            throw new RoomShareException(ExceptionType.emptyList);
        }
        return this.overdueList;
    }

    /**
     * Sets a task in the list as 'done' to mark that the user has completed the task.
     * Will not perform any operations if the index does not exist in the list.
     * @param index Index of the task to be marked as done.
     * @throws RoomShareException If the index cannot be found in the list of tasks.
     */
    public void done(int[] index) throws RoomShareException {
        if (index.length == 1) {
            if (index[0] < 0 || index[0] >= tasks.size()) {
                throw new RoomShareException(ExceptionType.outOfBounds);
            }
            tasks.get(index[0]).setDone(true);
        }
        else {
            if (index[0] < 0 || index[0] >= tasks.size() || index[1] < 0 || index[1] >= tasks.size()) {
                throw new RoomShareException(ExceptionType.outOfBounds);
            }
            for (int i = index[0]; i <= index[1]; i++){
                tasks.get(i).setDone(true);
            }
        }
    }

    /**
     * Searches for tasks that has the specified keyword and prints them to the console.
     * Will prompt that the search has no results if keyword does not exist in the list.
     * @param key Keyword of the search.
     */
    public void find (String key) {
        int queryCount = 1;
        for (Task query : tasks) {
            if (query.toString().toLowerCase().contains(key.trim())) {
                System.out.println("\t" + queryCount + ". " + query.toString());
                queryCount += 1;
            }
        }
        if (queryCount == 1) {
            System.out.println("    Your search returned no results.... Try searching with another keyword!");
        }
    }

    /**
     * Returns the entire ArrayList of tasks
     * @return tasks The ArrayList of Task objects that is being operated on.
     */
    public static ArrayList<Task> currentList() {
        return tasks;
    }

    /**
     * replaces the task at the specified index with a new task
     * @param index index of the task to be replaced
     * @param replacement the replacement task
     */
    public void replace(int index, Task replacement) {
        tasks.set(index, replacement);
    }

    /**
     * Sets priority of task at an index to a new priority
     * @param info the information of the task index and the priority it should be set to
     * @throws RoomShareException when the priority specified is wrong or index is out of bounds
     */
    public void setPriority(String[] info) throws RoomShareException {
        try {
            int index = Integer.parseInt(info[0]) - 1;
            Priority priority = Priority.valueOf(info[1]);
            tasks.get(index).setPriority(priority);
        } catch (IllegalArgumentException a) {
            throw new RoomShareException(ExceptionType.wrongPriority);
        } catch (IndexOutOfBoundsException i) {
            throw new RoomShareException(ExceptionType.outOfBounds);
        }

    }

    /**
     * Returns priority of the task in the form of an integer
     * high = 0, medium = 1, low = 2
     * @param t task in which we are checking the value of
     * @return integer value of the task's priority
     */
    public static int getValue(Task t) {
        if (t.getPriority().equals(Priority.high)) {
            return 0;
        } else if (t.getPriority().equals(Priority.medium)) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * Changes taskList sort mode
     * @param sortType new sort mode
     */
    public static void changeSort(SortType sortType) {
        TaskList.sortType = sortType;
        sortTasks();
    }

    /**
     * Sorts the list based on current sort mode
     * @throws IllegalArgumentException when the sort type is not of priority, alphabetical or by deadline
     */
    public static void sortTasks() {
        switch (sortType) {
        case priority:
            comparePriority();
            break;
        case alphabetical:
            compareAlphabetical();
            break;
        case deadline:
            compareDeadline();
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + sortType);
        }
    }

    /**
     * Compare tasks based on priority
     */
    public static void comparePriority() {
        Collections.sort(tasks, (task1, task2) -> {
            if( task1.getDone() && !task2.getDone() ) {
                return 1;
            } else if( task2.getDone() && !task1.getDone() ) {
                return -1;
            } else {
                return getValue(task1) - getValue(task2);
            }
        });
    }

    /**
     * Compare tasks based on Alphabetical order
     */
    public static void compareAlphabetical() {
        Collections.sort(tasks, (task1, task2) -> {
            if( task1.getDone() && !task2.getDone() ) {
                return 1;
            } else if( task2.getDone() && !task1.getDone() ) {
                return -1;
            } else {
                String name1 = task1.getDescription();
                String name2 = task2.getDescription();
                return name1.compareTo(name2);
            }
        });
    }

    /**
     * Compare tasks based on Deadline
     */
    public static void compareDeadline() {
        Collections.sort(tasks, (task1, task2) -> {
            if( task1.getDone() && !task2.getDone() ) {
                return 1;
            } else if( task2.getDone() && !task1.getDone() ) {
                return -1;
            } else {
                Date date1 = task1.getDate();
                Date date2 = task2.getDate();
                return (int) (date1.getTime() - date2.getTime());
            }
        });
    }

    /**
     * Reorder the positions of two tasks inside the task list
     * @param first the first task
     * @param second the second task
     */
    public void reorder(int first, int second) throws RoomShareException {
        try {
            Collections.swap(tasks, first, second);
        } catch (IndexOutOfBoundsException e) {
            throw new RoomShareException(ExceptionType.outOfBounds);
        }
    }

    /**
     * Snooze a specific task indicated by user
     * @param index the index of the task to be snoozed
     * @param amount the amount of time to snooze
     * @param timeUnit unit for snooze time: month, day, hour, minute
     * @throws IndexOutOfBoundsException when the specified index is not within the task list indices
     */
    public void snooze (int index, int amount, TimeUnit timeUnit) throws RoomShareException {
        try {
            switch (timeUnit) {
            case month:
                tasks.get(index).snoozeMonth(amount);
                break;
            case day:
                tasks.get(index).snoozeDay(amount);
                break;
            case hours:
                tasks.get(index).snoozeHour(amount);
                break;
            case minutes:
                tasks.get(index).snoozeMinute(amount);
                break;
            }
        }
        catch (IndexOutOfBoundsException e) {
            throw new RoomShareException(ExceptionType.outOfBounds);
        }
    }

    /**
     * Get the number of tasks inside the task list
     * @return the number of tasks inside the task list
     */
    public int getSize() {
        int count =0;
        for(Task t : tasks) {
            if(!t.getOverdue()) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * Get the number of completed tasks inside the task list
     * @return the number of completed tasks inside the task list
     */
    public int getDoneSize(){
        int count = 0;
        for (Task t: tasks){
            if (t.getDone() && !t.getOverdue()) count++;
        }
        return count;
    }

    /**
     * Retrieve a task from the list
     * @param index the index of the task
     * @return the task at the specified index of the task list
     * @throws RoomShareException when the index specified is out of bounds
     */
    public static Task get(int index) throws RoomShareException{
        try {
            return tasks.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new RoomShareException(ExceptionType.outOfBounds);
        }
    }

    /**
     * Returns current sort type of list
     * @return current sort type of list
     */
    public static SortType getSortType() { return sortType; }

    /**
     * lists out all the tasks associated with a certain assignee
     * will include tasks that are tagged "everyone", since everyone includes the assignee
     * @param user assignee to the tasks
     */
    public int[] listTagged(String user) {
        int listCount = 1;
        int doneCount  = 0;
        for (Task output : tasks) {
            if (output.getAssignee().equals(user) || output.getAssignee().equals("everyone")) {
                if (output.getDone()) {
                    doneCount += 1;
                }
                System.out.println("\t" + listCount + ". " + output.toString());
                if( output instanceof Assignment && !(((Assignment) output).getSubTasks() == null) ) {
                    ArrayList<String> subTasks = ((Assignment) output).getSubTasks();
                    for (String subtask : subTasks) {
                        System.out.println("\t" + "\t" + "- " + subtask);
                    }
                }
            }
            listCount += 1;
        }
        int[] done = {listCount - 1, doneCount};
        return done;
    }
}
