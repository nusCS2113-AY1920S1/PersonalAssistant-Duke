package compal.logic.parser;

import compal.commons.LogUtils;
import compal.logic.command.Command;
import compal.logic.command.FindFreeSlotCommand;
import compal.logic.parser.exceptions.ParserException;

import java.text.ParseException;
import java.util.Date;
import java.util.logging.Logger;

//@@author Catherinetan99
public class FindFreeSlotCommandParser implements CommandParser {

    private static final Logger logger = LogUtils.getLogger(FindFreeSlotCommandParser.class);
    public static final String MESSAGE_INVALID_DATE = "Error: Date entered cannot be a past date!";
    public static final String MESSAGE_INVALID_PARAM = "Whoops! Looks like there's an invalid parameter inserted!\n"
            + "This is how you use the findfreeslot command:\n\n" + FindFreeSlotCommand.MESSAGE_USAGE;

    @Override
    public Command parseCommand(String restOfInput) throws ParserException, ParseException {
        logger.info("Attempting to parse findfreeslot command");

        String[] args = restOfInput.split(" ");

        if (args.length > 6) {
            throw new ParserException(MESSAGE_INVALID_PARAM);
        }

        Date date = getDate(restOfInput);
        if (!isFutureDate(date)) {
            throw new ParserException(MESSAGE_INVALID_DATE);
        }
        int hour = getHour(restOfInput);
        int min = getMin(restOfInput);
        logger.info("Successfully parse findfreeslot command");
        return new FindFreeSlotCommand(date, hour, min);
    }
}
