package duke.logic.parsers.commandparser;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.logic.commands.Command;
import duke.logic.commands.PromptCommand;

/**
 * Constructs PromptCommand object.
 */
public class PromptParser extends CommandParser {

    /**
     * Constructs PromptCommand with cancel message.
     *
     * @return PromptCommand object
     */
    @Override
    public Command parse() {
        return new PromptCommand(Messages.PROMPT_CANCEL);
    }
}
