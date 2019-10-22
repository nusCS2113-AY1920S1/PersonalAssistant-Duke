package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.FindFreeSlotCommand;
import compal.logic.parser.exceptions.ParserException;

import java.text.ParseException;
import java.util.Date;

public class FindFreeSlotParser implements CommandParser {

    @Override
    public Command parseCommand(String restOfInput) throws ParserException, ParseException {
        Date date = null;
        try {
            date = getDate(restOfInput);
        } catch (ParserException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int hour = getHour(restOfInput);
        int min = getMin(restOfInput);
        return new FindFreeSlotCommand(date, hour, min);
    }
}
