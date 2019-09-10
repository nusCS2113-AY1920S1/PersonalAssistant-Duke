package duke.task;

/**
 * This class overrides multiple functions of TaskList for ease of testing.
 */
public class TaskListTest extends TaskList {
    private boolean isEmpty = true;

    /**
     * Overrides size function
     * @return 1 or 0 depending whether the TaskList has 1 or 0 task
     */
    @Override
    public int size() {
        return isEmpty ? 0 : 1;
    }

    /**
     * Adds task to TaskList
     * @param task The task to be added.
     */
    @Override
    public void add(Task task) {
        this.isEmpty = false;
    }

    /**
     * Deletes task from TaskList
     * @param i The index of the task to be retrieved, starting from 1.
     */
    @Override
    public void delete(int i) {
        this.isEmpty = true;
    }

    /**
     * Get information of Task.
     * @param i The index of the task to be retrieved, starting from 1.
     * @return Task object with information required
     */
    @Override
    public Task get(int i) {
        return new TaskTest();
    }
}