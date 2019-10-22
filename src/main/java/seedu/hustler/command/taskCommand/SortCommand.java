package seedu.hustler.command.taskCommand;

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
     * @param rawInput raw user's input.
     */
    public SortCommand(String rawInput) {
        this.sortType = rawInput.substring(1);
    }

    /**
     * Sorts the task list.
     */
    public void execute() {
        Hustler.list.sortTask(sortType);
    }
}
