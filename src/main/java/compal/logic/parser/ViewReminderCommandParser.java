package compal.logic.parser;

import compal.commons.LogUtils;
import compal.logic.command.Command;
import compal.logic.command.ViewReminderCommand;
import compal.logic.parser.exceptions.ParserException;

import java.util.logging.Logger;

//@@author Catherinetan99
public class ViewReminderCommandParser implements CommandParser {

    private static final Logger logger = LogUtils.getLogger(ViewReminderCommandParser.class);

    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        logger.info("Attempting to parse view-reminder command");
        logger.info("Successfully parse view-reminder command");
        return new ViewReminderCommand();
    }
}
