package command;

import parser.CommandParams;
import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Represents a specified command as ListCommand by extending the <code>Command</code> class.
 * Lists all tasks in taskList of Duke.
 * Responses with the result.
 */
public class ListCommand extends Command {
    /**
     * Constructs a <code>ListCommand</code> object.
     *
     * @param commandParams parameters used to invoke the command.
     */
    public ListCommand(CommandParams commandParams) {
        super(commandParams);
    }

    /**
     * Lists all tasks in taskList of Duke by using ui of Duke.
     *
     * @param tasks   The taskList of Duke.
     * @param ui      The ui of Duke.
     * @param storage The storage of Duke.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.getSize() == 0) {
            ui.println("Ops, you haven't added any task!");
        } else {
            ui.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.getSize(); i++) {
                ui.println((i + 1) + "." + tasks.getTaskInfo(i));
            }
        }
    }
}
