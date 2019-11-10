package compal.logic.parser;


import compal.commons.LogUtils;
import compal.logic.command.ByeCommand;
import compal.logic.command.Command;
import compal.logic.parser.exceptions.ParserException;

import java.util.logging.Logger;

//@@author SholihinK
public class ByeCommandParser implements CommandParser {
    private static final Logger logger = LogUtils.getLogger(ByeCommandParser.class);
    public static final String MESSAGE_INVALID_PARAM = "Whoops! Looks like that's an invalid command!\n"
        + "This is how you use the bye command:\n\n" + ByeCommand.MESSAGE_USAGE;

    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        logger.info("Attempting to parse bye command");
        if (!restOfInput.isEmpty()) {
            throw new ParserException(MESSAGE_INVALID_PARAM);
        }

        logger.info("Successfully parse bye command");
        return new ByeCommand();
    }
}
