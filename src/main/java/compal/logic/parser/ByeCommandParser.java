package compal.logic.parser;

import compal.logic.command.ByeCommand;
import compal.logic.command.Command;
import compal.logic.parser.exceptions.ParserException;

public class ByeCommandParser implements CommandParser {
    @Override
    public Command parseCommand(String restOfUserInput) throws ParserException {
        return new ByeCommand();
    }
}
