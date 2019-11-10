package compal.logic.parser;

import compal.commons.LogUtils;
import compal.logic.command.Command;
import compal.logic.command.ListCommand;
import compal.logic.parser.exceptions.ParserException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class ListCommandParser implements CommandParser {
    private static final Logger logger = LogUtils.getLogger(ListCommand.class);
    private static final ArrayList<String> key = new ArrayList<>(Arrays.asList(TOKEN_STATUS, TOKEN_TYPE));
    public static final String MESSAGE_INVALID_PARAM = "Whoops! Looks like there's an invalid "
        + "parameter inserted!\n" + "This is how you use the list command:\n\n" + ListCommand.MESSAGE_USAGE;

    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        logger.info("Attempting to parse list command");

        isValidKey(key, restOfInput, MESSAGE_INVALID_PARAM);

        if (restOfInput.isEmpty()) {
            logger.info("Successfully parse list command");
            return new ListCommand("");
        } else {
            if (restOfInput.contains(TOKEN_TYPE) && restOfInput.contains(TOKEN_STATUS)) {
                String[] args = restOfInput.split(" ");
                if (args.length > 4) {
                    throw new ParserException(MESSAGE_INVALID_PARAM);
                }
                String type = getType(restOfInput);
                String status = getTokenStatus(restOfInput);
                logger.info("Successfully parse list command");
                return new ListCommand(type, status);
            } else if (restOfInput.contains(TOKEN_STATUS) && !restOfInput.contains(TOKEN_TYPE)) {
                String[] args = restOfInput.split(" ");
                if (args.length > 2) {
                    throw new ParserException(MESSAGE_INVALID_PARAM);
                }
                String status = getTokenStatus(restOfInput);
                logger.info("Successfully parse list command");
                return new ListCommand("", status);
            } else if (!restOfInput.contains(TOKEN_STATUS) && restOfInput.contains(TOKEN_TYPE)) {
                String[] args = restOfInput.split(" ");
                if (args.length > 2) {
                    throw new ParserException(MESSAGE_INVALID_PARAM);
                }
                String type = getType(restOfInput);
                logger.info("Successfully parse list command");
                return new ListCommand(type);
            } else {
                throw new ParserException(MESSAGE_INVALID_PARAM);
            }
        }
    }
}
