package compal.logic.parser;

import compal.commons.LogUtils;
import compal.logic.command.Command;
import compal.logic.command.ViewReminderCommand;
import compal.logic.parser.exceptions.ParserException;

import java.util.logging.Logger;

//@@author Catherinetan99
public class ViewReminderCommandParser implements CommandParser {

    private static final Logger logger = LogUtils.getLogger(ViewReminderCommandParser.class);
    public static final String MESSAGE_INVALID_PARAM = "Whoops! Looks like there's an invalid parameter inserted!\n"
            + "This is how you use the view-reminder command:\n\n" + ViewReminderCommand.MESSAGE_USAGE;

    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        logger.info("Attempting to parse view-reminder command");

        if (restOfInput != null && !restOfInput.trim().isEmpty()) {
            throw new ParserException(MESSAGE_INVALID_PARAM);
        }

        logger.info("Successfully parse view-reminder command");
        return new ViewReminderCommand();
    }
}
