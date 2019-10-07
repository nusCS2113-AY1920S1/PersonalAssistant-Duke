package duke.logic.command.shortcut;

import duke.logic.command.Command;
import duke.logic.command.CommandResult;
import duke.logic.command.Undoable;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.shortcut.Shortcut;

public class SetShortcutCommand extends Command implements Undoable {
    public static final String COMMAND_WORD = "short";
    public static final String MESSAGE_SET_SUCCESS = "Shortcut [%s] is set.";
    public static final String MESSAGE_REMOVE_SUCCESS = "Shortcut [%s] is removed.";
    public static final String MESSAGE_EMPTY_SHORTCUT = "Shortcut cannot be empty.";

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
            model.setShortcut(shortcut);
            return new CommandResult(String.format(MESSAGE_SET_SUCCESS, shortcut.getName()));
        }
    }

    @Override
    public void undo(Model model) throws CommandException {

    }

    @Override
    public void redo(Model model) throws CommandException {

    }
}
