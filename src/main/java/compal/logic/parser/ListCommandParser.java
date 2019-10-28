package compal.logic.parser;

import compal.commons.LogUtils;
import compal.logic.command.Command;
import compal.logic.command.ListCommand;
import compal.logic.parser.exceptions.ParserException;

import java.util.logging.Logger;

public class ListCommandParser implements CommandParser {
    private static final Logger logger = LogUtils.getLogger(ListCommand.class);

    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        logger.info("Attempting to parse list command");

        if (restOfInput.isEmpty()) {
            logger.info("Successfully parse list command");
            return new ListCommand("");
        } else {
            String type = getType(restOfInput);

            logger.info("Successfully parse list command");
            return new ListCommand(type);
        }
    }
}
