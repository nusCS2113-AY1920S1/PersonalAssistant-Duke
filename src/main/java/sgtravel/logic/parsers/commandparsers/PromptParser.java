package sgtravel.logic.parsers.commandparsers;

import sgtravel.logic.commands.Command;
import sgtravel.logic.commands.PromptCommand;

/**
 * Parser for a prompt command.
 */
public class PromptParser {

    /**
     * Constructs the PromptCommand.
     *
     * @param prompt The prompt as a String.
     * @return The PromptCommand.
     */
    public static Command parseCommand(String prompt) {
        return new PromptCommand(prompt);
    }
}
