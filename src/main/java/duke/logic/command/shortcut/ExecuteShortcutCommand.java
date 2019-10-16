package duke.logic.command.shortcut;

import duke.logic.command.Command;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.parser.commons.BakingHomeParser;
import duke.logic.parser.exceptions.ParseException;
import duke.model.Model;
import duke.model.shortcut.Shortcut;

import static java.util.Objects.requireNonNull;

/**
 * A command to execute a series of pre-specified commands.
 */
public class ExecuteShortcutCommand extends Command {
    public static final String COMMAND_WORD = "do";

    private static final String MESSAGE_COMMIT = "Execute shortcut";
    private static final String MESSAGE_SHORTCUT_NOT_FOUND = "Shortcut not found";
    private static final String MESSAGE_SUCCESS = "Shortcut executed successfully";
    private static final String MESSAGE_EXECUTION_FAILED = "Execution of [%s] failed: [%s]";

    private final String shortcutName;
    private Shortcut toExecute;

    /**
     * Creates {@code ExecuteShortcutCommand}.
     *
     * @param shortcutName The name of the shortcut. Must be non-empty.
     */
    public ExecuteShortcutCommand(String shortcutName) {
        requireNonNull(shortcutName);

        this.shortcutName = shortcutName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        getShortcut(model);

        //Disable version control so that commands executed are not added to undo/redo list.
        model.setVersionControl(false);

        BakingHomeParser parser = new BakingHomeParser();
        for (String line : toExecute.getUserInputs()) {
            try {
                Command command = parser.parseCommand(line);
                command.execute(model);
            } catch (CommandException | ParseException e) {
                throw new CommandException(String.format(MESSAGE_EXECUTION_FAILED, line, e.getMessage()));
            }
        }

        //Enable version control.
        model.setVersionControl(true);

        model.commit(MESSAGE_COMMIT);

        return new CommandResult(MESSAGE_SUCCESS);
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
