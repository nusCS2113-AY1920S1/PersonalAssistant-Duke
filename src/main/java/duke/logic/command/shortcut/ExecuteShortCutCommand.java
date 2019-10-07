package duke.logic.command.shortcut;

import duke.logic.command.Command;
import duke.logic.command.CommandResult;
import duke.logic.command.Undoable;
import duke.logic.command.exceptions.CommandException;
import duke.logic.command.order.EditOrderCommand;
import duke.logic.parser.commons.BakingHomeParser;
import duke.logic.parser.exceptions.ParseException;
import duke.model.Model;
import duke.model.shortcut.Shortcut;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class ExecuteShortCutCommand extends Command implements Undoable {
    public static final String COMMAND_WORD = "do";

    public static final String MESSAGE_SHORTCUT_NOT_FOUND = "Shortcut not found";
    public static final String MESSAGE_SUCCESS = "Shortcut executed successfully";
    public static final String MESSAGE_EXECUTION_FAILED = "Execute [%s] failed: %s";

    private final String shortcutName;
    private Shortcut toExecute;

    public ExecuteShortCutCommand(String shortcutName) {
        requireNonNull(shortcutName);

        this.shortcutName = shortcutName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        getShortcut(model);

        BakingHomeParser parser = new BakingHomeParser();
        for (String line : toExecute.getUserInputs()) {
            try {
                CommandResult commandResult;
                Command command = parser.parseCommand(line);
                commandResult = command.execute(model);
            } catch (CommandException | ParseException e) {
                throw new CommandException(String.format(MESSAGE_EXECUTION_FAILED, line, e.getMessage()));
            }
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void undo(Model model) throws CommandException {

    }

    @Override
    public void redo(Model model) throws CommandException {

    }

    private void getShortcut(Model model) throws CommandException {
        for (Shortcut shortcut : model.getShortcutList()) {
            if (shortcut.getName().equals(shortcutName)) {
                this.toExecute = shortcut;
                return;
            }
        }

        throw new CommandException(MESSAGE_SHORTCUT_NOT_FOUND);
    }
}
