package command;

import task.TaskList;
import ui.Ui;
import storage.Storage;

/**
 * Represents a specified command as ExitCommand by extending the <code>Command</code> class.
 * Terminates the loop in <code>main</code> method of Duke.
 * Responses with the result.
 */
public class ExitCommand extends Command {
    /**
     * Constructs an <code>ExitCommand</code> object.
     */
    public ExitCommand() {
        super("exit");
    }

    /**
     * Shows bye to user.
     *
     * @param tasks The taskList of Duke.
     * @param ui The ui of Duke.
     * @param storage The storage of Duke.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
    ui.println("Bye. Hope to see you again soon!");
    }

}
