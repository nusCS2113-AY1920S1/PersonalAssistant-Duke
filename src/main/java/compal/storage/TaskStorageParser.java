package compal.storage;

import compal.model.tasks.Deadline;
import compal.model.tasks.Event;
import compal.model.tasks.RecurringTask;
import compal.model.tasks.Task;

//@@author jaedonkey
/**
 * Returns Task object based on parts of data stored in the string array.
 *
 */
public class TaskStorageParser implements StorageParser<Task> {

    private static final String SYMBOL_RECUR = "RT";
    private static final String SYMBOL_DEADLINE = "D";
    private static final String SYMBOL_EVENT = "E";


    @Override
    public Task parseData(String[] parts) {
        switch (parts[1]) {
        case SYMBOL_DEADLINE:
            return new Deadline(parts[2], stringToPriority(parts[4]), parts[5], parts[7]);
        case SYMBOL_RECUR:
            return new RecurringTask(parts[2], stringToPriority(parts[4]), parts[5], parts[6], parts[7]);
        case SYMBOL_EVENT:
            return new Event(parts[2], stringToPriority(parts[4]), parts[5], parts[6], parts[7]);
        default:
            System.out.println("Storage:LOG: Could not parse text. Returning what we managed to parse.");
            return null;

        }
    }


    /**
     * Returns Priority from a String describing the priority level.
     *
     * @param priority task priority string
     * @return Priority enum
     */
    private Task.Priority stringToPriority(String priority) {
        return Task.Priority.valueOf(priority.toLowerCase());
    }

}
