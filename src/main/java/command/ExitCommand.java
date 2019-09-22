package command;

import parser.CommandParams;
import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Represents a specified command as ExitCommand by extending the {@code Command} class.
 * Terminates the loop in {@code main} method of Duke.
 * Responses with the result.
 */
public class ExitCommand extends Command {
    /**
     * Constructs an {@code ExitCommand} object.
     *
     * @param commandParams parameters used to invoke the command.
     */
    public ExitCommand(CommandParams commandParams) {
        super(commandParams);
    }

    /**
     * Shows bye to user.
     *
     * @param tasks   The taskList of Duke.
     * @param ui      The ui of Duke.
     * @param storage The storage of Duke.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.println("Bye. Hope to see you again soon!");
        System.exit(0);
    }

}
