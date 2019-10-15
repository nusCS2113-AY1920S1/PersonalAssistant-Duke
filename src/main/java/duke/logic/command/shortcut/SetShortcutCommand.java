package duke.logic.command.shortcut;

import duke.logic.command.Command;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.shortcut.Shortcut;

/**
 * A command to remove or add a {@code Shortcut}.
 */
public class SetShortcutCommand extends Command {
    public static final String COMMAND_WORD = "short";

    private static final String MESSAGE_COMMIT = "Set shortcut";
    private static final String MESSAGE_SET_SUCCESS = "Shortcut [%s] is set.";
    private static final String MESSAGE_REMOVE_SUCCESS = "Shortcut [%s] is removed.";
    private static final String MESSAGE_EMPTY_SHORTCUT = "Shortcut cannot be empty.";
    private static final String MESSAGE_CANNOT_CONTAIN_DO_COMMAND = "Commands cannot contain do commands.";
    private final Shortcut shortcut;
    private final boolean isEmptyShortcut;

    /**
     * Creates a {@code SetShortCutCommand}.
     * @param shortcut to add or remove.
     *                 If {@code shortcut} has empty {@code userInputs} and it is in
     *                 the Shortcut List, it will be removed.
     *                 If {@code shortcut} has non-empty {@code userInputs}, it will be added to Shortcut List,
     *                 or override an existing shortcut in Shortcut List.
     */
    public SetShortcutCommand(Shortcut shortcut) {
        this.shortcut = shortcut;
        isEmptyShortcut = shortcut.getUserInputs().get(0).equals("");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        //If shortcut has empty user inputs and it is in the Shortcut List
        if (isEmptyShortcut && model.hasShortcut(shortcut)) {
            model.removeShortcut(shortcut);
            return new CommandResult(String.format(MESSAGE_REMOVE_SUCCESS, shortcut.getName()));
        } else if (isEmptyShortcut) {
            return new CommandResult(MESSAGE_EMPTY_SHORTCUT);
        } else {
            checkShortcut();

            model.setShortcut(shortcut);

            model.commit(MESSAGE_COMMIT);

            return new CommandResult(String.format(MESSAGE_SET_SUCCESS, shortcut.getName()));
        }
    }

    /**
     * Prevents possible dead loops when a shortcut has a reference to another shortcut command in its user inputs.
     * @throws CommandException if the shortcut has a reference to another shortcut command.
     */
    private void checkShortcut() throws CommandException {
        for (String line : shortcut.getUserInputs()) {
            if (line.split(" ")[0].equals(ExecuteShortcutCommand.COMMAND_WORD)) {
                throw new CommandException(MESSAGE_CANNOT_CONTAIN_DO_COMMAND);
            }
        }
    }
}
