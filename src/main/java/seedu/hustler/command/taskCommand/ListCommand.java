package seedu.hustler.command.taskCommand;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;

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
