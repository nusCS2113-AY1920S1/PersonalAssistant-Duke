package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.parser.exceptions.ParserException;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public interface CommandParser {
    Command parseCommand(String input) throws ParseException, ParserException;

    /**
     * Check if the date input is of valid format.
     *
     * @param inputDateStr the string of the date input
     * @return true or false.
     */
    default Boolean isValidInputDate(String inputDateStr) {
        String regex = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputDateStr);

        if (!matcher.matches()) {
            return false;
        }

        return true;
    }
}