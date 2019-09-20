package wallet.logic.parser;

import wallet.logic.command.Command;

/**
 * Parses input of user.
 */
public interface Parser<T extends Command> {
    /**
     * Parses user input into a command and returns it.
     * @param input User input of command.
     */
    T parse(String input);
}
