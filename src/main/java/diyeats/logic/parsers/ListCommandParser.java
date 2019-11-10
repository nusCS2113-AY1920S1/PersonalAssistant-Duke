package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.commands.ListCommand;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author GaryStu
/**
 * Parser class to handle a list command.
 */
public class ListCommandParser implements ParserInterface<ListCommand> {
    private static Logger logger = Logger.getLogger(ListCommandParser.class.getName());

    /**
     * Parse userInput and return ListCommand.
     * @param userInputStr String input by user.
     * @return <code>ListCommand</code>
     */
    @Override
    public ListCommand parse(String userInputStr) throws ProgramException {
        HashMap<String, String> argumentInfoMap;
        LocalDate localDate = LocalDate.now();
        String sortArgStr = "";
        if (userInputStr.isBlank()) {
            logger.log(Level.INFO, "there is no argument for list, take the default value");
            return new ListCommand();
        }
        logger.log(Level.INFO, "there is argument for list command, start parsing");
        argumentInfoMap = ArgumentSplitter.splitForwardSlashArguments(userInputStr);
        for (String details : argumentInfoMap.keySet()) {
            if (details.equals("date")) {
                String dateArgStr = "";
                try {
                    dateArgStr = argumentInfoMap.get(details);
                    localDate = LocalDate.parse(dateArgStr, dateFormat);
                } catch (DateTimeParseException e) {
                    logger.log(Level.WARNING, "date cannot be parsed");
                    return new ListCommand(false, "Unable to parse \"" + userInputStr + "\" as a date.");
                }
            }
            if (details.equals("sort")) {
                sortArgStr = argumentInfoMap.get(details).trim();
                if (!(sortArgStr.equals("costAscending") || sortArgStr.equals("calorieAscending")
                    || sortArgStr.equals("costDescending") || sortArgStr.equals("calorieDescending"))) {
                    logger.log(Level.WARNING, "the sorting arguments are not valid");
                    throw new ProgramException("The only valid sorting arguments are costAscending, calorieAscending," +
                            " , costDescending, and calorieDescending");
                }
            }
        }
        logger.log(Level.FINE, "arguments are successfully parsed");
        return new ListCommand(localDate, sortArgStr);
    }
}
