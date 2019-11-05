package duke.logic.parsers.commandparser;

import duke.logic.commands.Command;
import duke.logic.commands.PromptCommand;

/**
 * Parser for a prompt command.
 */
public class PromptParser {
    public static Command parseCommand(String prompt) {
        return new PromptCommand(prompt);
    }
}
