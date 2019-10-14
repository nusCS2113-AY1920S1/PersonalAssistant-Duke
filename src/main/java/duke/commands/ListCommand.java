package duke.commands;

import duke.storage.Storage;

/**
 * Class representing a command to list items in a task list.
 */
public class ListCommand extends Command {
    /**
     * Executes this command on the given task list and user interface.
     *
     * @param storage The duke.storage object containing task list.
     */
    @Override
    public CommandResult execute(Storage storage) {
        return new CommandResult(storage.getTasks());
    }
}
