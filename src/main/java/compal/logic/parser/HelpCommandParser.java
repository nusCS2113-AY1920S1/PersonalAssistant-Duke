package compal.logic.parser;

import compal.commons.LogUtils;
import compal.logic.command.Command;
import compal.logic.command.HelpCommand;
import compal.logic.parser.exceptions.ParserException;

import java.util.logging.Logger;

//@@author LTPZ
public class HelpCommandParser implements CommandParser {
    private static final Logger logger = LogUtils.getLogger(HelpCommand.class);

    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        logger.info("Attempting to parse help command");
        logger.info("Successfully parse help command");
        return new HelpCommand(restOfInput);
    }
}
