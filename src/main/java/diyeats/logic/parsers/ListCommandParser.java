package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.commands.ListCommand;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

//@@author GaryStu
/**
 * Parser class to handle a list command.
 */
public class ListCommandParser implements ParserInterface<ListCommand> {

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
            return new ListCommand();
        }
        argumentInfoMap = ArgumentSplitter.splitForwardSlashArguments(userInputStr);
        for (String details : argumentInfoMap.keySet()) {
            if (details.equals("date")) {
                String dateArgStr = "";
                try {
                    dateArgStr = argumentInfoMap.get(details);
                    localDate = LocalDate.parse(dateArgStr, dateFormat);
                } catch (DateTimeParseException e) {
                    return new ListCommand(false, "Unable to parse \"" + userInputStr + "\" as a date.");
                }
            }
            if (details.equals("sort")) {
                sortArgStr = argumentInfoMap.get(details).trim();
                if (!(sortArgStr.equals("cost") || sortArgStr.equals("calorie"))) {
                    throw new ProgramException("The only valid sorting arguments are cost or calorie.");
                }
            }
        }
        return new ListCommand(localDate, sortArgStr);
    }
}
