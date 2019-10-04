package seedu.hustler.command;

import seedu.hustler.Hustler;

/**
 * Command that lists tasks in TaskList instance.
 */
public class ListCommand extends Command {
    public ListCommand() {

    }
    
    /**
     * Lists commands in TaskList instance.
     *
     */
    public void execute() {
        Hustler.list.displayList();
    }
}
