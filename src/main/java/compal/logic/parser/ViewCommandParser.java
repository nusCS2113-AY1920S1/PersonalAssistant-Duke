package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.ListCommand;
import compal.logic.parser.exceptions.ParserException;

public class ViewCommandParser implements CommandParser {


    @Override
    public Command parseCommand(String userInput) throws ParserException {
        String[] args = userInput.split(" ", 2);
        String restOfUserInput;
        if (args.length > 2) {
            restOfUserInput = args[1];
        } else {
            throw new ParserException("SOMTHING");
        }

        return null;
    }
}
