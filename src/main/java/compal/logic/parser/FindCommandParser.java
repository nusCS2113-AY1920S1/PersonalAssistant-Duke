package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.FindCommand;
import compal.logic.parser.exceptions.ParserException;

public class FindCommandParser implements CommandParser {


    @Override
    public Command parseCommand(String input) throws ParserException {
        return new FindCommand(input);
    }


}
