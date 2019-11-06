package sgtravel.logic.parsers.commandparsers;

import sgtravel.logic.commands.Command;
import sgtravel.logic.commands.PromptCommand;

/**
 * Parser for a prompt command.
 */
public class PromptParser {
    public static Command parseCommand(String prompt) {
        return new PromptCommand(prompt);
    }
}
