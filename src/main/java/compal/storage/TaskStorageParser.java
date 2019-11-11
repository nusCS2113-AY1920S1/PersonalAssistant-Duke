package compal.storage;

import compal.commons.LogUtils;
import compal.model.tasks.Deadline;
import compal.model.tasks.Event;
import compal.model.tasks.Task;

import java.util.logging.Logger;

//@@author jaedonkey
/**
 * Returns Task object based on parts of data stored in the string array.
 *
 */
public class TaskStorageParser implements StorageParser<Task> {

    private static final String SYMBOL_DEADLINE = "D";
    private static final String SYMBOL_EVENT = "E";
    private static final Logger logger = LogUtils.getLogger(TaskStorageParser.class);

    @Override
    public Task parseData(String[] parts) {
        switch (parts[1]) {
        case SYMBOL_DEADLINE:
            return new Deadline(parts[2], stringToPriority(parts[4]), parts[5], parts[8]);
        case SYMBOL_EVENT:
            return new Event(parts[2], stringToPriority(parts[4]), parts[5], parts[6], parts[7], parts[8]);
        default:
            logger.info("Could not parse text. Returning what we managed to parse.");
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
