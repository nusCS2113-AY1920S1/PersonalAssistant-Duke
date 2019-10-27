package seedu.hustler.logic.command.task;

import seedu.hustler.Hustler;
import seedu.hustler.logic.command.Command;

/**
 * Command that lists tasks in TaskList instance.
 */
public class ListCommand extends Command {
    /**
     * Lists commands in TaskList instance.
     */
    public void execute() {
        Hustler.list.displayList();
    }
}
