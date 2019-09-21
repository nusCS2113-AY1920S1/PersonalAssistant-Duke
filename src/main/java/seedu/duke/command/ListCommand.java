package seedu.duke.command;

import seedu.duke.task.TaskList;

/**
 * Command that lists tasks in TaskList instance.
 */
public class ListCommand extends Command {
    public ListCommand() {

    }
    
    /**
     * Lists commands in TaskList instance.
     *
     * @param list list of tasks 
     */
    public void execute(TaskList list) {
        list.displayList();
    }
}
