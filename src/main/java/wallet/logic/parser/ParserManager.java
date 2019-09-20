package wallet.logic.parser;

import wallet.logic.command.AddCommand;
import wallet.logic.command.Command;
import wallet.logic.command.EditCommand;
import wallet.logic.command.ExitCommand;
import wallet.logic.command.HelpCommand;
import wallet.logic.command.ListCommand;

public class ParserManager {
    /**
     * Parses the user input command and returns the corresponding Command object.
     * @param fullCommand The input of user.
     * @return The corresponding Command object.
     */
    public Command parseCommand(String fullCommand) {
        String[] command = fullCommand.split(" ", 2);

        switch (command[0]) {
        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(command[1]);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(command[1]);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(command[1]);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            //fallthrough

        default:
            return new HelpCommand();
        }
    }
}
