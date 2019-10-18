package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.ListCommand;
import compal.logic.parser.exceptions.ParseException;

public class ListCommandParser implements CommandParser {
    @Override
    public Command parseCommand(String restOfUserInput) throws ParseException {
        return new ListCommand();
    }
}
