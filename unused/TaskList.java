//@@author maxxyx96-unused
/**
 * Checks if a task is marked as done.
 *
 * @param keyDesc description of the task
 * @return returns true if task is marked as done, false otherwise.
 */
public boolean isTaskDone(String keyDesc) {
        ArrayList searchList = new ArrayList<Task>();
        for (Task searchTask : items) {
        if (searchTask.toString().contains(keyDesc)) {
        if (searchTask.isDone()) {
        return true;
        }
        }
        }
        return false;
        }//@@author