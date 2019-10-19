package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.ListCommand;
import compal.logic.parser.exceptions.ParserException;

public class ListCommandParser implements CommandParser {
    @Override
    public Command parseCommand(String restOfUserInput) throws ParserException {
        return new ListCommand();
    }
}
