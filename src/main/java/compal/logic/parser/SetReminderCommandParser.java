package compal.logic.parser;

import compal.commons.LogUtils;
import compal.logic.command.Command;
import compal.logic.command.SetReminderCommand;
import compal.logic.parser.exceptions.ParserException;

import java.util.logging.Logger;

//@@author Catherinetan99
public class SetReminderCommandParser implements CommandParser {

    private static final Logger logger = LogUtils.getLogger(SetReminderCommandParser.class);
    public static final String MESSAGE_INVALID_PARAM = "Whoops! Looks like there's an invalid parameter inserted!\n"
            + "This is how you use the set-reminder command:\n\n" + SetReminderCommand.MESSAGE_USAGE;

    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        logger.info("Attempting to parse set-reminder command");

        String[] args = restOfInput.split(" ");

        if (args.length > 4) {
            throw new ParserException(MESSAGE_INVALID_PARAM);
        }

        int taskID = getTaskID(restOfInput);
        String status = getTokenStatus(restOfInput);
        logger.info("Successfully parse set-reminder command");
        return new SetReminderCommand(taskID, status);
    }
}
