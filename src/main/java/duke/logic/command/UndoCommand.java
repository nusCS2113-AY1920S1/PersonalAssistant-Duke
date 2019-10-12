package duke.logic.command;

import duke.logic.command.exceptions.CommandException;
import duke.model.Model;

/**
 * Reverts the {@code model}'s baking home to its previous state.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    private static final String MESSAGE_UNDO_SUCCESS = "Undo success.";
    private static final String MESSAGE_UNDO_STACK_EMPTY = "No more tasks to be undone.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.canUndo()) {
            throw new CommandException(MESSAGE_UNDO_STACK_EMPTY);
        }

        model.undo();
        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }
}
