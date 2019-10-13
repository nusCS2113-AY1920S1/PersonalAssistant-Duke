package duke.commands;

import duke.storage.Storage;

/**
 * Class representing a command to exit duke.Duke.
 */
public class ExitCommand extends Command {
    private static final String MESSAGE_BYE = "Bye. Hope to see you again soon!\n";

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param storage The storage object containing task list.
     */
    @Override
    public CommandResult execute(Storage storage) {
        CommandResult commandResult = new CommandResult(MESSAGE_BYE);
        commandResult.setExit(true);
        return commandResult;
    }
}
