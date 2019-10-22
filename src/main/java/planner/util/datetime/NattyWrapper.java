package planner.util.datetime;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import planner.logic.exceptions.legacy.ModInvalidTimeException;

public class NattyWrapper {

    /**
     * Single instantiation of Natty Parser
     * to reduce start up impact.
     */
    private Parser nattyParser;

    /**
     * Constructor for wrapper class for Natty Library for date parsing.
     *
     */
    public NattyWrapper() {
        nattyParser = new Parser();
        //to speed parsing, introducing a dummy initialize case
        nattyParser.parse("the day after tomorrow");
    }

    /**
     * Main parser for Natty library, with reference to sample code below.
     * https://www.programcreek.com/java-api-examples/?api=com.joestelmach.natty.Parser.
     * @param input User date input.
     * @return Date of the user input if valid, null if invalid.
     */
    public Date runParser(String input) throws ModInvalidTimeException {
        List<DateGroup> groups = nattyParser.parse(input);
        if (groups.isEmpty()) {
            throw new ModInvalidTimeException();
        }
        List<Date> dates = groups.get(0).getDates();
        if (dates.isEmpty()) {
            throw new ModInvalidTimeException();
        }
        return dates.get(0);
    }

    /**
     * Main entry for LocalDateTime conversion.
     * @param input User input of date/time information.
     * @return Valid time based on user input.
     * @throws ModInvalidTimeException if user inputs an invalid date/time.
     */
    public LocalDateTime dateToLocalDateTime(String input) throws ModInvalidTimeException {
        Date date = runParser(input);
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
