package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.FindFreeSlotCommand;
import compal.logic.parser.exceptions.ParserException;

import java.text.ParseException;
import java.util.Date;

//@@author Catherinetan99
public class FindFreeSlotCommandParser implements CommandParser {

    public static final String MESSAGE_INVALID_DATE = "Error: Date entered cannot be a past date!";

    @Override
    public Command parseCommand(String restOfInput) throws ParserException, ParseException {
        Date date = getDate(restOfInput);
        if (!isFutureDate(date)) {
            throw new ParserException(MESSAGE_INVALID_DATE);
        }
        int hour = getHour(restOfInput);
        int min = getMin(restOfInput);
        return new FindFreeSlotCommand(date, hour, min);
    }
}
