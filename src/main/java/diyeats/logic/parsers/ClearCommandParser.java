package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.commands.ClearCommand;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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
        String[] startAndEndDates;
        LocalDate startDate;
        LocalDate endDate;

        try {
            InputValidator.validate(userInputStr);
            startAndEndDates = ArgumentSplitter.splitArguments(userInputStr, " ");
        } catch (ProgramException e) {
            return new ClearCommand(false, "Please enter 2 dates; "
                    + "Start and End dates to clear meals from.");
        }

        try {
            startDate = LocalDate.parse(startAndEndDates[0], dateFormat);
        } catch (DateTimeParseException e) {
            return new ClearCommand(false, "Unable to parse input " + startAndEndDates[0] + " as a date");
        }

        try {
            endDate = LocalDate.parse(startAndEndDates[1], dateFormat);
        } catch (DateTimeParseException e) {
            return new ClearCommand(false, "Unable to parse input " + startAndEndDates[1] + " as a date");
        }

        if (startDate.isAfter(endDate)) {
            return new ClearCommand(false, "Start date " + startDate + " is after end date " + endDate + ""
                    + "\nPlease ensure start date is before end date");
        }

        return new ClearCommand(startDate, endDate);
    }
}
