package duke.logic.parsers;

import duke.logic.commands.ListCommand;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

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
    public ListCommand parse(String userInputStr) {
        HashMap<String, String> argumentInfoMap;
        LocalDate localDate = LocalDate.now();

        if (userInputStr.isBlank()) {
            System.out.println("Blank");
            return new ListCommand();
        }
        argumentInfoMap = ArgumentSplitter.splitForwardSlashArguments(userInputStr);
        for (String details : argumentInfoMap.keySet()) {
            if (details.equals("date")) {
                String dateArgStr = "";
                try {
                    dateArgStr = argumentInfoMap.get(details);
                    localDate= LocalDate.parse(dateArgStr, dateFormat);
                } catch (DateTimeParseException e) {
                    return new ListCommand(false, "Unable to parse \"" + userInputStr + "\" as a date.");
                }
            }
            if (details.equals("sort")) {
                String sortArgStr = argumentInfoMap.get(details).trim();
                if (sortArgStr.equals("cost")) {
                    return new ListCommand(localDate, sortArgStr);
                }
                else if (sortArgStr.equals("calorie")) {
                    return new ListCommand(localDate, sortArgStr);
                }
            }
        }
        return new ListCommand();
    }
}
