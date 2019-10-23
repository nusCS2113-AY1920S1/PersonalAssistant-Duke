package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.HelpCommand;
import compal.logic.parser.exceptions.ParserException;

//@@author LTPZ
public class HelpCommandParser implements CommandParser {
    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        if (hasToken(restOfInput, TOKEN_COMMAND)) {
            String command = getCommand(restOfInput);
            return new HelpCommand(command);
        } else {
            return new HelpCommand();
        }
    }
}
