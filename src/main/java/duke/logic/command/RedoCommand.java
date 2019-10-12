package duke.logic.command;

import duke.logic.command.exceptions.CommandException;
import duke.model.Model;

/**
 * Reverts the {@code model}'s baking home to its previously undone state.
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    private static final String MESSAGE_REDO_SUCCESS = "Redo success.";
    private static final String MESSAGE_REDO_STACK_EMPTY = "No more tasks to be redone.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.canRedo()) {
            throw new CommandException(MESSAGE_REDO_STACK_EMPTY);
        }

        model.redo();
        return new CommandResult(MESSAGE_REDO_SUCCESS);
    }
}
