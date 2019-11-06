package compal.logic.parser;

import compal.commons.LogUtils;
import compal.logic.command.Command;
import compal.logic.command.FindCommand;
import compal.logic.parser.exceptions.ParserException;

import java.util.logging.Logger;

public class FindCommandParser implements CommandParser {
    private static final Logger logger = LogUtils.getLogger(FindCommandParser.class);


    @Override
    public Command parseCommand(String input) throws ParserException {
        logger.info("Attempting to parse find command");
        return new FindCommand(input);
    }


}
