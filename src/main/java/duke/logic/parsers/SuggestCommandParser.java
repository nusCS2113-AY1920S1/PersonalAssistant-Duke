package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.SuggestCommand;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Parser class to handle suggestion of meals.
 */
public class SuggestCommandParser implements ParserInterface<SuggestCommand> {

    private static final String dateArgStr = "date";
    private static final String displayArgStr = "display";
    private int maxMealsToSuggest = 5;

    /**
     * Parse user input and return SuggestCommand.
     * @param userInputStr String input by user
     * @return <code>SuggestCommand</code> Command object that will process meal suggestions.
     * @throws DukeException If the userInput cannot be parsed
     */
    @Override
    public SuggestCommand parse(String userInputStr) throws DukeException {
        InputValidator.validate(userInputStr);
        HashMap<String, String> argumentsMap = ArgumentSplitter.splitForwardSlashArguments(userInputStr);
        Calendar suggestionDate = Calendar.getInstance();
        if (argumentsMap.containsKey("date")) {
            try {
                suggestionDate.setTime(dateFormat.parse(argumentsMap.get(dateArgStr)));
            } catch (ParseException e) {
                return new SuggestCommand(true, "Unable to parse" + dateArgStr + " as a date. " +
                        "Please follow DD/MM/YYYY format.");
            }
        }

        if (argumentsMap.containsKey(displayArgStr)) {
            try {
                maxMealsToSuggest = Integer.parseInt(argumentsMap.get(displayArgStr).trim());
            } catch (NumberFormatException e) {
                return new SuggestCommand(true, "Unable to parse display as integer. Please " +
                        "input as integer. eg: /display 5");
            }
        }

        return new SuggestCommand(suggestionDate, maxMealsToSuggest);
    }
}
