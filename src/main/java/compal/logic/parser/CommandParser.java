package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.parser.exceptions.ParserException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public interface CommandParser {
    Command parseCommand(String input) throws ParseException, ParserException;

    /**
     * Check if the date input is of valid format.
     *
     * @param date the string of the date input
     * @return true or false.
     */
    default boolean isDateValid(String date) {
        final  String DATE_FORMAT = "dd/MM/yyyy";
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}