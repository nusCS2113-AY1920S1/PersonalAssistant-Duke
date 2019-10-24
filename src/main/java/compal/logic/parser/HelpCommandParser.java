package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.HelpCommand;
import compal.logic.parser.exceptions.ParserException;

//@@author LTPZ
public class HelpCommandParser implements CommandParser {
    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        return new HelpCommand(restOfInput);
    }
}
