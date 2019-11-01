package duke.logic.command;

import duke.logic.command.exceptions.CommandException;
import duke.model.Model;

public class ExitCommand extends Command {
    public static final String COMMAND_WORD = "exit";

    public static final String AUTO_COMPLETE_INDICATOR = COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(true);
    }
}
