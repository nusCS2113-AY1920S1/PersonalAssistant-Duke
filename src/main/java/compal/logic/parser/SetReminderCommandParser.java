package compal.logic.parser;

import compal.commons.LogUtils;
import compal.logic.command.Command;
import compal.logic.command.SetReminderCommand;
import compal.logic.parser.exceptions.ParserException;

import java.util.logging.Logger;

//@@author Catherinetan99
public class SetReminderCommandParser implements CommandParser {

    private static final Logger logger = LogUtils.getLogger(SetReminderCommandParser.class);

    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        logger.info("Attempting to parse set-reminder command");
        int taskID = getTaskID(restOfInput);
        String status = getTokenStatus(restOfInput);
        logger.info("Successfully parse set-reminder command");
        return new SetReminderCommand(taskID, status);
    }
}
