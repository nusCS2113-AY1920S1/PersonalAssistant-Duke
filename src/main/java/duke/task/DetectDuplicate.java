package duke.task;

/**
 * Finds duplicated tasks and alerts the user.
 */
public class DetectDuplicate {
    protected TaskList items;
    private static final int ZERO = 0;

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
     * @param command Type of task keyed in.
     * @param description The input keyed in by the user.
     * @return Boolean that states if the input is a duplicate.
     */
    public boolean isDuplicate(String command, String description) {
        if ("todo".equals(command) || "fixedduration".equals(command)) {
            for (int i = ZERO; i < items.size(); i++) {
                if (items.get(i).isContain(description)) {
                    //contains, is implemented in Task.java
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }
}