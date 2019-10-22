package seedu.hustler.command.task;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;

/**
 * Command that sorts the task list.
 */
public class SortCommand extends Command {
    /**
     * User input that contains the way to sort the tasks.
     */
    private String sortType;

    /**
     * Initializes the sortType.
     *
     * @param sortType type of sort.
     */
    public SortCommand(String sortType) {
        this.sortType = sortType;
    }

    /**
     * Sorts the task list.
     */
    public void execute() {
        Hustler.list.sortTask(sortType);
    }
}
