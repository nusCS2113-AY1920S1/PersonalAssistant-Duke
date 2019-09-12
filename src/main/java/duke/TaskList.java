package duke;

import duke.tasks.Task;

import java.util.ArrayList;

/**
 * Class used to store the task list and perform necessary manipulations to the task list such as
 * adding tasks, removing tasks and finding tasks based on keywords, as well as obtaining the size
 * of the task list.
 */
public class TaskList {

    private ArrayList<Task> list = new ArrayList<>();

    /**
     * Removes an element from the task list.
     *
     * @param index the index of the duke.tasks.Task in the task list that is to be removed
     */
    public void remove(int index) {
        list.remove(index);
    }

    /**
     * Adds an element to the task list.
     *
     * @param t the duke.tasks.Task object to be added to the task list.
     */
    public void add(Task t) {
        list.add(t);
    }

    /**
     * Returns a subset of the task list (implemented as an ArrayList of duke.tasks.Task objects) that contains
     * the query specified in the argument.
     *
     * @param query the search query to be obtained from the input command
     * @return the ArrayList of duke.tasks.Task objects whose description contained the query
     */
    public ArrayList<Task> findTask(String query) {
        ArrayList<Task> result = new ArrayList<>();
        for (Task t: list) {
            if (t.getDescription().contains(query)) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * Returns the current size of the task list.
     *
     * @return the current size of the task list.
     */
    public int getSize() {
        return list.size();
    }

    /**
     * Returns the task list for duke.Duke, which is implemented as an ArrayList of duke.tasks.Task objects.
     *
     * @return the task list
     */
    public ArrayList<Task> getTaskList() {
        return list;
    }

}
