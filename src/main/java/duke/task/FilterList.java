package duke.task;

import java.util.ArrayList;

//@@author talesrune
/**
 * Represents a filter list that stores a list of filtered tasks.
 */
public class FilterList {
    protected ArrayList<Task> filterList;
    protected int filterIndex = -1;
  
    /**
     * Creates an empty task list using an array list.
     */
    public FilterList() {
        filterList = new ArrayList<Task>();
    }

    /**
     * Creates an updated task list with the specified array list.
     *
     * @param filterList The updated array list.
     */
    public FilterList(ArrayList<Task> filterList) {
        this.filterList = filterList;
    }

    /**
     * Adds a task into the task list.
     *
     * @param taskObj a task to be added.
     */
    public void add(Task taskObj) {
        filterList.add(taskObj);
    }

    /**
     * Clears all tasks from the filter list.
     */
    public void clear() {
        filterList.clear();
    }
    
    /**
     * Retrieves the task from the filter list via the task's index.
     *
     * @param index The index of task.
     * @return Task which is retrieved from the index.
     */
    public Task get(int index) {
        return filterList.get(index);
    }

    /**
     * Retrieves the task from the filter list via the task's index.
     *
     * @return int which is retrieve from the filter index.
     */
    public int getFilterIndex() {
        return filterIndex;
    }

    /**
     * Set the filter index based on the type of task.
     *
     *  @param filterIndex The index of filtered task.
     */
    public void setFilterIndex(int filterIndex) {
        this.filterIndex = filterIndex;
    }


    /**
     * Gets the size of the task list.
     *
     * @return int that represents the task list size.
     */

    public int size() {
        return filterList.size();
    }
}