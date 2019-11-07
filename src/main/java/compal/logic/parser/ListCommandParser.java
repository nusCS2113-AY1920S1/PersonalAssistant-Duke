package compal.logic.parser;

import compal.commons.LogUtils;
import compal.logic.command.Command;
import compal.logic.command.ListCommand;
import compal.logic.parser.exceptions.ParserException;

import java.util.logging.Logger;

public class ListCommandParser implements CommandParser {
    private static final Logger logger = LogUtils.getLogger(ListCommand.class);
    public static final String MESSAGE_INVALID_PARAM = "Whoops! Looks like there's an invalid "
        + "parameter inserted!\n" + "This is how you use the list command:\n\n" + ListCommand.MESSAGE_USAGE;

    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        logger.info("Attempting to parse list command");


        if (restOfInput.isEmpty()) {
            logger.info("Successfully parse list command");
            return new ListCommand("");
        } else {
            String[] args = restOfInput.split(" ");
            System.out.println(args.length);

            if (args.length > 2) {
                throw new ParserException(MESSAGE_INVALID_PARAM);
            }

            String type = getType(restOfInput);

            logger.info("Successfully parse list command");
            return new ListCommand(type);
        }
    }
}
