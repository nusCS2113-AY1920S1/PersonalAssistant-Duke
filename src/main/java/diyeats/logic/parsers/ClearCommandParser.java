package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.commands.ClearCommand;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

//@@author HashirZahir
/**
 * Parser class to handle multiple deletions of meals from model.
 */
public class ClearCommandParser implements ParserInterface<ClearCommand> {

    /**
     * Parse user input and return ClearCommand.
     * @param userInputStr String input by user.
     * @return <code>ClearCommand</code> Command object demarcating the range of data between dates to be deleted
     */

    @Override
    public ClearCommand parse(String userInputStr) {
        HashMap<String, String> startAndEndDates;
        LocalDate startDate;
        LocalDate endDate;

        try {
            InputValidator.validate(userInputStr);
            startAndEndDates = ArgumentSplitter.splitForwardSlashArguments(userInputStr);
        } catch (ProgramException e) {
            return new ClearCommand(false, "Please enter 2 dates; "
                    + "Start and End dates to clear meals from.");
        }

        System.out.println(startAndEndDates.keySet());
        if (!startAndEndDates.containsKey("startdate")) {
            return new ClearCommand(false, "Please include a startdate"
                    + " using /startdate.");
        }

        try {
            startDate = LocalDate.parse(startAndEndDates.get("startdate"), dateFormat);
        } catch (DateTimeParseException e) {
            return new ClearCommand(false, "Unable to parse input " + startAndEndDates.get("startdate") + " as a date");
        }

        if (!startAndEndDates.containsKey("enddate")) {
            return new ClearCommand(false, "Please include an enddate"
                    + " using /enddate.");
        }

        try {
            endDate = LocalDate.parse(startAndEndDates.get("enddate"), dateFormat);
        } catch (DateTimeParseException e) {
            return new ClearCommand(false, "Unable to parse input " + startAndEndDates.get("enddate") + " as a date");
        }

        if (startDate.isAfter(endDate)) {
            return new ClearCommand(false, "Start date " + startDate + " is after end date " + endDate + ""
                    + "\nPlease ensure start date is before end date");
        }

        return new ClearCommand(startDate, endDate);
    }
}
