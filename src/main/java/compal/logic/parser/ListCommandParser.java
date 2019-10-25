package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.ListCommand;
import compal.logic.parser.exceptions.ParserException;

public class ListCommandParser implements CommandParser {
    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        if (restOfInput.isEmpty()) {
            return new ListCommand("");
        } else {
            String type = getType(restOfInput);
            return new ListCommand(type);
        }
    }
}
