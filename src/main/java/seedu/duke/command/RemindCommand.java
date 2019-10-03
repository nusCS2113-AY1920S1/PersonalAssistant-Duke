package seedu.duke.command;

import seedu.duke.task.TaskList;
import seedu.duke.task.Reminders;

/**
 * Command that executes reminders.
 */
public class RemindCommand extends Command {
    
    /**
     * Executes remind pipeline.
     *
     * @param list list of tasks.
     */
    public void execute(TaskList list) {
        Reminders.runAll(list);
        Reminders.displayReminders();
    } 
}
