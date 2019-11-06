package compal.logic.parser;

import compal.commons.LogUtils;
import compal.logic.command.Command;
import compal.logic.command.DoneCommand;

import compal.logic.parser.exceptions.ParserException;

import java.util.logging.Logger;

/**
 * Parses input arguments and creates a new DoneCommand object.
 */
//@@author SholihinK
public class DoneParser implements CommandParser {
    private static final Logger logger = LogUtils.getLogger(DoneParser.class);
    public static final String MESSAGE_INVALID_PARAM = "Whoops! Looks like there's an invalid parameter inserted!\n"
        + "This is how you use the done command:\n\n" + DoneCommand.MESSAGE_USAGE;

    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        logger.info("Attempting to parse done command");
        int taskID = getTaskID(restOfInput);

        String[] args = restOfInput.split(" ");

        if (args.length > 4) {
            throw new ParserException(MESSAGE_INVALID_PARAM);
        }

        String status = getTokenStatus(restOfInput);
        logger.info("Successfully parse done command");
        return new DoneCommand(taskID, status);
    }
}
