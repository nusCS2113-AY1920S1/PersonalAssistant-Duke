package compal.logic.parser;

import compal.commons.LogUtils;
import compal.logic.command.Command;
import compal.logic.command.QuoteCommand;
import compal.logic.parser.exceptions.ParserException;

import java.util.logging.Logger;

public class QuoteCommandParser implements CommandParser {
    private static final Logger logger = LogUtils.getLogger(QuoteCommand.class);

    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        logger.info("Attempting to parse quote command");
        logger.info("Successfully parse quote command");
        return new QuoteCommand(restOfInput);
    }
}

