package duke.logic.command.shortcut;

import duke.logic.command.Command;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.shortcut.Shortcut;

public class SetShortcutCommand extends Command {
    public static final String COMMAND_WORD = "short";

    private static final String MESSAGE_SET_SUCCESS = "Shortcut [%s] is set.";
    private static final String MESSAGE_REMOVE_SUCCESS = "Shortcut [%s] is removed.";
    private static final String MESSAGE_EMPTY_SHORTCUT = "Shortcut cannot be empty.";
    private static final String MESSAGE_CANNOT_CONTAIN_DO_COMMAND = "Commands cannot contain do commands.";
    private final Shortcut shortcut;
    private final boolean isEmptyShortcut;

    public SetShortcutCommand(Shortcut shortcut) {
        this.shortcut = shortcut;
        isEmptyShortcut = shortcut.getUserInputs().get(0).equals("");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (isEmptyShortcut && model.hasShortcut(shortcut)) {
            model.removeShortcut(shortcut);
            return new CommandResult(String.format(MESSAGE_REMOVE_SUCCESS, shortcut.getName()));
        } else if (isEmptyShortcut) {
            return new CommandResult(MESSAGE_EMPTY_SHORTCUT);
        } else {
            checkShortcut();

            model.setShortcut(shortcut);

            model.commit();

            return new CommandResult(String.format(MESSAGE_SET_SUCCESS, shortcut.getName()));
        }
    }

    private void checkShortcut() throws CommandException {
        for (String line : shortcut.getUserInputs()) {
            if (line.split(" ")[0].equals(ExecuteShortcutCommand.COMMAND_WORD)) {
                throw new CommandException(MESSAGE_CANNOT_CONTAIN_DO_COMMAND);
            }
        }
    }
}
