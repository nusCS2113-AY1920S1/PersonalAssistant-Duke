package duke.task;

import duke.enums.Numbers;

/**
 * Finds duplicated tasks and alerts the user.
 */
//@@author e0318465
public class DetectDuplicate {
    protected TaskList items;

    /**
     * Creates a DetectDuplicate task with a list of TaskList available.
     *
     * @param items List of tasks already in the system.
     */
    public DetectDuplicate(TaskList items) {
        this.items = items;
    }

    /**
     * Checks if the input is already in the list.
     *
     * @param description The description of the task.
     * @return Boolean that states if the input is a duplicate.
     */
    public boolean isDuplicate(String description) {
        for (int i = Numbers.ZERO.value; i < items.size(); i++) {
            if (items.get(i).isContain(description)) {
                return true;
            }
        }
        return false;
    }
}